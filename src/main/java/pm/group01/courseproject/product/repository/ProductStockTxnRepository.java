package pm.group01.courseproject.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pm.group01.courseproject.product.model.ProductStockTxn;

/*
 * @author Battuguldur Ganbold (986874)
 */

@Repository
public interface ProductStockTxnRepository extends JpaRepository<ProductStockTxn, Integer> {

}
