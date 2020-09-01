package pm.group01.courseproject.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pm.group01.courseproject.product.model.ProductStock;

import java.util.List;

/*
 * @author Battuguldur Ganbold (986874)
 */

@Repository
public interface ProductStockRepository extends JpaRepository<ProductStock, Integer> {
    ProductStock findAllByProduct_ProductID(Integer id);
    List<ProductStock> findAllByProduct_SellerId(Integer id);
}
