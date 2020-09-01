package pm.group01.courseproject.product.service;

import pm.group01.courseproject.product.model.ProductStock;

import java.util.List;

/*
 * @author Battuguldur Ganbold (986874)
 */

public interface ProductStockService extends BaseService<ProductStock> {

    boolean delete(ProductStock productStock);
    ProductStock findByProductId(Integer productId);
}
