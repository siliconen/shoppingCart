package pm.group01.courseproject.order.integrations;
/*this class is created to do domain related tasks , like checking out shopping cart,  */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import pm.group01.courseproject.order.event.ProductSoldEvent;
import pm.group01.courseproject.order.model.*;
import pm.group01.courseproject.order.repository.OrderLineRepository;
import pm.group01.courseproject.order.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/*
 * Authored with surgical percison by Islam Ahmad
 * */
@Service
public class OrderDomainService {
    @Value("${endUserUrl}")
    String endUserUrl;
    @Value("${cartUrl}")
    String cartUrl;
    @Value("${paymentUrl}")
    String paymentUrl;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderLineRepository orderLineRepository;
    @Autowired
    RestOperations restClient;
    @Autowired
    ApplicationEventPublisher publisher;

    public List<OrderElement> checkOut(ShoppingCartToOrderDTO shoppingCartToOrderDTO, Integer sessionUserId) {
        List<CartLineForOrderDTO> cartLineForOrderDTOList = shoppingCartToOrderDTO.getShoppinCartToOrderDTOList();
        List<OrderElement> orderElements = new ArrayList<>();
        for (CartLineForOrderDTO cartLine : cartLineForOrderDTOList) {
            OrderLine orderLine = new OrderLine(cartLine.getProductId(), cartLine.getQuantity(), cartLine.getProductDesc(), cartLine.getPrice(), cartLine.getSellerId());
            OrderElement order = new OrderElement();
            orderLine.setOrderClass(order);
            Integer userId = shoppingCartToOrderDTO.getEndUserId();
            if (!userId.equals(sessionUserId)) return null;
            order.setOrderStatus(OrderStatus.CREATED);
            order.setEndUserId(userId);
            order.setSellerId(orderLine.getSellerId());
            /*return user's address id or -1 if no address */
            /* [addressId] from localhost:8090/enduser/{userId}/address */
            Integer addressId = restClient.getForObject("http://localhost:8090/enduser/" + userId + "/address/", Integer.class);
            if (addressId != -1) {
                order.setBillingAddressId(addressId);
                order.setShippingAddressId(addressId);
//                System.out.println(order);
                order.setOrderTotal(0.0);
                order = orderRepository.save(order);
                order.setOrderTotal(order.getOrderTotal() + orderLine.getUnitPrice() * orderLine.getQuantity());
                order.setOrderLine(orderLine);
                order = orderRepository.save(order);
                orderElements.add(order);
                return orderElements;
            }

        }
        return null;
    }

    public OrderElement cancelOrder(Integer orderId, Integer userId) {
//        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        Optional<OrderElement> optionalOrder = orderRepository.findByIdAndUserId(orderId, userId);
        if (optionalOrder.isPresent()) {
            OrderElement order = optionalOrder.get();
            /*if not shipped, then can be canceled*/
            if (order.getOrderStatus() != OrderStatus.SHIPPED) {
                order.setOrderStatus(OrderStatus.CANCELED);
                orderRepository.save(order);
                return order;
            }
        }
        return null;
    }

//    /*will call GET localhost:8090/cart/{userId}/clear/*/
//    public void clearCart(Integer userId) {
//        restClient.getForObject(cartUrl + userId + "clear/", Object.class);
//    }

    public OrderElement charge(Integer orderId, Payment payment) {
        Optional<OrderElement> retrievedOrder = orderRepository.findById(orderId);
        if (retrievedOrder.isPresent() && retrievedOrder.get().getOrderStatus() == OrderStatus.CREATED) {
            OrderElement order = retrievedOrder.get();
            /*POST localhost:8090/payment/getpaymentid/*/
            Integer retrievedPaymentId = restClient.postForObject(paymentUrl + "getpaymentid/", payment, Integer.class);
            System.out.println(retrievedPaymentId);
            Double totalAmount = order.getOrderTotal();
            if (retrievedPaymentId != null && retrievedPaymentId >= 1) {
                ResponseEntity<Boolean> result = restClient.postForEntity(paymentUrl + "/charge/" + totalAmount, payment, Boolean.class);
                System.out.println("inside first condition");
                if (result.hasBody()){
                    if (result.getBody()) {
                        System.out.println("inside second condition");
                        order.setOrderStatus(OrderStatus.CONFIRMED);
                        OrderLine orderLine = order.getOrderLine();
                        publisher.publishEvent(new ProductSoldEvent(orderLine.getProductId(), orderLine.getQuantity(), orderLine.getSellerId()));
                        return order;
                    }
                }
            }
        }
        return null;
    }
}