
package pm.group01.courseproject.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pm.group01.courseproject.cart.model.*;
import pm.group01.courseproject.cart.service.ShoppingCartService;
import pm.group01.courseproject.securityconfig.jwt.util.JwtUtil;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:8090"}, maxAge = 6000, allowedHeaders = "*")
@RestController
@RequestMapping("/cartLine/api/v1")
public class ShoppingCartController {
    @Autowired
    ShoppingCartService shoppingCartService;
    @Autowired
    private JwtUtil jwtTokenUtil;
    String token = "";

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody CartLineUi cartLineUi, @RequestHeader(name = "Authorization") String token) {
        CartLine cartLine = null;
        token = token.replaceAll("Bearer", "").trim();

        if (cartLineUi != null) {
            cartLine = new CartLine();
            cartLine.setProductName(cartLineUi.getProductName());
            cartLine.setPrice(cartLineUi.getPrice());
            cartLine.setProductDesc(cartLineUi.getProductDesc());
            cartLine.setProductId(cartLineUi.getProductId());
            cartLine.setQuantity(cartLineUi.getQuantity());
            cartLine.setCategoryId(cartLineUi.getCategoryId());
            cartLine.setImageLink(cartLineUi.getImageLink());
            cartLine.setCategoryName(cartLineUi.getCategoryName());
            cartLine.setSellerId(cartLineUi.getSellerId());
            shoppingCartService.save(cartLine, Integer.parseInt(jwtTokenUtil.extractId(token).trim()));
        }
        if (cartLine == null)
            return new ResponseEntity<>("Error happened please revise your parameters", HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>("Item added successfully", HttpStatus.OK);
    }


    @PutMapping("/edit")
    public ResponseEntity<?> updateCart(@RequestBody UpdatedCartLine updatedCartLine, @RequestHeader(name = "Authorization") String token) {
        token = token.replaceAll("Bearer", "").trim();


        boolean result = shoppingCartService.updateCart(updatedCartLine, Integer.parseInt(jwtTokenUtil.extractId(token)));
        if (!result)
            return new ResponseEntity<>("Error happened please revise your parameters", HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>("Item edited Successfully", HttpStatus.OK);

    }


    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCart(@RequestBody DeletedCartLine deletedCartLine, @RequestHeader(name = "Authorization") String token) {
        token = token.replaceAll("Bearer", "").trim();
        boolean result = shoppingCartService.DeleteCart(deletedCartLine, Integer.parseInt(jwtTokenUtil.extractId(token)));
        if (!result)
            return new ResponseEntity<>("Error happened please revise your parameters", HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>("Item deleted Successfully", HttpStatus.OK);
    }

    @GetMapping("/getCartItems")
    public CartUiToFrontEnd getItemsList(@RequestHeader(name = "Authorization") String token) {
        token = token.replaceAll("Bearer", "").trim();
        List<CartLineUi> lineForUI = shoppingCartService.findAllByID(Integer.parseInt(jwtTokenUtil.extractId(token))).stream()
                .map(p -> new CartLineUi(p.getProductId(), p.getQuantity(), p.getProductDesc(), p.getPrice(), p.getProductName(), p.getCategoryId(), p.getImageLink(), p.getSellerId(), p.getCategoryName()))
                .collect(Collectors.toList());
        CartUiToFrontEnd cartUiToFrontEnd = new CartUiToFrontEnd();
        cartUiToFrontEnd.setUser_id(Integer.parseInt(jwtTokenUtil.extractId(token)));
        cartUiToFrontEnd.setCartLineUiList(lineForUI);
        return cartUiToFrontEnd;

    }

    @GetMapping("/getItems/{userId}")
    public ShoppinCartToOrderDTO getCartItems(@PathVariable Integer userId) {
        List<CartLineForOrderDTO> lineForOrderDTOS = shoppingCartService.findAllByID(userId).stream()
                .map(p -> new CartLineForOrderDTO(p.getProductId(), p.getQuantity(), p.getProductDesc(), p.getPrice(), p.getSellerId()))
                .collect(Collectors.toList());
        ShoppinCartToOrderDTO shoppinCartToOrderDTO = new ShoppinCartToOrderDTO();
        shoppinCartToOrderDTO.setEndUserId(userId);
        shoppinCartToOrderDTO.setShoppinCartToOrderDTOList(lineForOrderDTOS);
        return shoppinCartToOrderDTO;

    }

    @GetMapping("/{userId}/clear")
    public ResponseEntity<?> deleteAllByUserId(@PathVariable Integer userId) {
        boolean result = shoppingCartService.DeleteAllCartElements(userId);
        if (!result)
            return new ResponseEntity<>("Error happened please revise your parameters", HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>("shopping cart elements deleted  Successfully", HttpStatus.OK);
    }
}