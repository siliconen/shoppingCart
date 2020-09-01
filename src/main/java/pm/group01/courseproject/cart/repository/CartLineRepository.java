package pm.group01.courseproject.cart.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pm.group01.courseproject.cart.model.CartLine;

@Repository
public interface CartLineRepository extends CrudRepository<CartLine, Integer> {
}
