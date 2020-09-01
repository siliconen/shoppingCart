package pm.group01.courseproject.cart.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pm.group01.courseproject.cart.model.ShoppingCart;

@Repository
public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Integer> {

    ShoppingCart findByEndUserId(Integer userId);


}
