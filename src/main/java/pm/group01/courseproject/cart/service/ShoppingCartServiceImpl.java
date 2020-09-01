package pm.group01.courseproject.cart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pm.group01.courseproject.cart.model.CartLine;
import pm.group01.courseproject.cart.model.DeletedCartLine;
import pm.group01.courseproject.cart.model.ShoppingCart;
import pm.group01.courseproject.cart.model.UpdatedCartLine;
import pm.group01.courseproject.cart.repository.CartLineRepository;
import pm.group01.courseproject.cart.repository.ShoppingCartRepository;

import java.util.ArrayList;
import java.util.List;
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService{
    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    @Autowired
    CartLineRepository cartLineRepository;

    @Override
    public CartLine save(CartLine cartLine,Integer UserId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByEndUserId(UserId);
        int quantity = cartLine.getQuantity();
        if(shoppingCart != null)
        {
            boolean find = false;
            for(CartLine cartLine1:shoppingCart.getCartLineList())
            {
                if(cartLine1.getProductId().equals(cartLine.getProductId()))
                {
                  cartLine = cartLine1;
                  shoppingCart.getCartLineList().remove(cartLine1);
                  find=true;
                  break;
                }
            }
            if(find)
            {
                cartLine.setQuantity(quantity);
            }

                shoppingCart.getCartLineList().add(cartLine);
                cartLine.setCart(shoppingCart);
                shoppingCartRepository.save(shoppingCart);

        }
        else
        {
          shoppingCart = new ShoppingCart();
          shoppingCart.setEndUserId(UserId);
          List<CartLine>CartList = new ArrayList<>();
          cartLine.setCart(shoppingCart);
          CartList.add(cartLine);
          shoppingCart.setCartLineList(CartList);
          shoppingCartRepository.save(shoppingCart);
        }

        return cartLine;
    }

    @Override
    public List<CartLine> findAllByID(Integer UserId) {
        ShoppingCart shoppingCart = null;
         shoppingCart = shoppingCartRepository.findByEndUserId(UserId);
        if(shoppingCart == null)
        {
            shoppingCart = new ShoppingCart();
            shoppingCart.setEndUserId(UserId);
            List<CartLine>CartList = new ArrayList<>();
            shoppingCart.setCartLineList(CartList);
            shoppingCartRepository.save(shoppingCart);
        }
        return shoppingCart.getCartLineList();
    }



    @Override
    public boolean updateCart(UpdatedCartLine updatedCartLine,Integer userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByEndUserId(userId);
        if(shoppingCart!=null) {
            CartLine cartLine =  null;

            cartLine = shoppingCart.getCartLineList().stream()
                    .filter(c -> c.getProductId().equals(updatedCartLine.getProductId()))
                    .findAny()
                    .orElse(null);
            if(cartLine==null)
                return false;
            shoppingCart.getCartLineList().remove(cartLine);
            cartLine.setQuantity(updatedCartLine.getQuantity());
            shoppingCart.getCartLineList().add(cartLine);
            shoppingCartRepository.save(shoppingCart);
return true;
        }
        return false;
    }

    @Override
    public boolean DeleteCart(DeletedCartLine deletedCartLine,Integer userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByEndUserId(userId);
        if (shoppingCart != null) {
            CartLine cartLine = null;
            cartLine = shoppingCart.getCartLineList().stream()
                    .filter(c -> c.getProductId().equals(deletedCartLine.getProductId()))
                    .findAny()
                    .orElse(null);
            if(cartLine==null)
                return false;
            shoppingCart.getCartLineList().remove(cartLine);
            shoppingCartRepository.save(shoppingCart);

            return true;
        }
        return false;
    }

    @Override
    public boolean DeleteAllCartElements(Integer userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByEndUserId(userId);
        if (shoppingCart != null) {
            shoppingCart.getCartLineList().clear();
            shoppingCartRepository.save(shoppingCart);
            return true;
        }
        return false;
    }

    @Override
    public boolean DeleteShoppingCart(Integer userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByEndUserId(userId);
        shoppingCartRepository.delete(shoppingCart);

        return true;
    }

}
