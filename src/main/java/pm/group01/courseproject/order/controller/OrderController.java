package pm.group01.courseproject.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestOperations;
import pm.group01.courseproject.order.integrations.OrderDomainService;
import pm.group01.courseproject.order.model.*;
import pm.group01.courseproject.order.repository.OrderLineRepository;
import pm.group01.courseproject.order.repository.OrderRepository;
import pm.group01.courseproject.order.service.OrderService;
import pm.group01.courseproject.securityconfig.jwt.util.JwtUtil;

import java.util.List;
/*
 * Authored with surgical percison by Islam Ahmad
 * */
@RequestMapping("/orders")
@RestController
public class OrderController {
    @Autowired
    OrderDomainService orderDomainService;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderLineRepository orderLineRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    private JwtUtil jwtTokenUtil;
    @Autowired
    RestOperations restClient;

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("/checkout")
    public ResponseEntity<?> checkOutCart(@RequestBody ShoppingCartToChecoutUI shoppingCartToChecoutUI, @RequestHeader(name = "Authorization") String token) {
        token = token.replaceAll("Bearer", "").trim();
        String userId = jwtTokenUtil.extractId(token);
        Integer addressId = restClient.getForObject("http://localhost:8090/enduser/" + userId + "/address/", Integer.class);
        if (addressId == -1) return null;
        List<OrderElement> orders = orderDomainService.checkOut(shoppingCartToChecoutUI.getShoppingCartToOrderDTO(), Integer.parseInt(userId));
        if (orders == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        else
            for (OrderElement orderElement : orders) {
//                System.out.println("Before charge");
//                System.out.println(shoppingCartToChecoutUI.getPayment());
                orderDomainService.charge(orderElement.getOrderId(),shoppingCartToChecoutUI.getPayment() );
//                System.out.println("after charge");
            }
//            restClient.getForEntity("http://localhost:8090/cartLine/api/v1/"+userId+"/clear",Object.class);
            return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    /*client should check for order status after cancelation*/
    @GetMapping("/cancel/{orderId}")
    public OrderElement cancel(@PathVariable String orderId,  @RequestHeader (name="Authorization") String token) {
        token = token.replaceAll("Bearer","").trim();
        String userId = jwtTokenUtil.extractId(token);
        return orderDomainService.cancelOrder(Integer.parseInt(orderId), Integer.parseInt(userId));
    }

    @PreAuthorize("hasAnyAuthority('SELLER')")
    @GetMapping("{orderId}/ship")
    public OrderElement ship(@PathVariable String orderId, @RequestHeader(name = "Authorization") String token) {
        token = token.replaceAll("Bearer", "").trim();
        String sellerId = jwtTokenUtil.extractId(token);
        OrderElement sellerOrder = orderService.findByIdAndSellerId(Integer.parseInt(orderId), Integer.parseInt(sellerId));
        OrderElement order = orderService.findById(Integer.parseInt(orderId));
        if (order != null) {
            order.setOrderStatus(OrderStatus.SHIPPED);
            order = orderService.save(order);
        }
        return order;
    }

    @PreAuthorize("hasAnyAuthority('SELLER')")
    @GetMapping("/")
    public List<OrderElement> getOrderElementsForSeller(@RequestHeader(name = "Authorization") String token) {
        token = token.replaceAll("Bearer", "").trim();
        List<OrderElement> orderElementList;
        String sellerId = jwtTokenUtil.extractId(token);
        orderElementList = orderRepository.findAllBySellerId(Integer.parseInt(sellerId));
        return orderElementList;
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @GetMapping("/all")
    public List<OrderElement> getOrderElementsForEndUsers(@RequestHeader(name = "Authorization") String token) {
        token = token.replaceAll("Bearer", "").trim();
        List<OrderElement> orderElementList;
        String endUserId = jwtTokenUtil.extractId(token);
        orderElementList = orderRepository.findAllByEndUserId(Integer.parseInt(endUserId));
        return orderElementList;
    }
}