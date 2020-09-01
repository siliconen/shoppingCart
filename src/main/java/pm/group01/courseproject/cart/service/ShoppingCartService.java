package pm.group01.courseproject.cart.service;

import pm.group01.courseproject.cart.model.DeletedCartLine;
import pm.group01.courseproject.cart.model.UpdatedCartLine;
import pm.group01.courseproject.cart.model.CartLine;

import java.util.List;

public interface ShoppingCartService {

    CartLine save(CartLine cartLine,Integer UserId);

    List<CartLine> findAllByID(Integer UserId);

    boolean updateCart (UpdatedCartLine updatedCartLine,Integer userId);
    boolean DeleteCart (DeletedCartLine deletedCartLine,Integer userId);
    boolean DeleteAllCartElements (Integer userId);
    boolean DeleteShoppingCart (Integer userId);


}
