package pm.group01.courseproject.product.service;

import pm.group01.courseproject.product.model.Product;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/*
 * @author Battuguldur Ganbold (986874)
 */

public interface ProductService extends BaseService<Product> {

    public List<Product> findByCategoryIs(Integer categoryId);

    public List<Product> findAllBySomeFields(String name, String desc);

    public List<Product> findAllBySomeFieldsWithPrice(String name, String desc, double startPrice, double endPrice);

    public List<Product> findByProductsInCategory(Integer categoryId, String name, String desc);

    public List<Product> findByProductsInCategoryWithPrice(Integer categoryId, String name, String desc, double startPrice, double endPrice);

    public Function<List<Product>, Map<String, Long>> getProductCountByCategoryName =
            (list) -> list.stream().collect(
                    Collectors.groupingBy(e -> e.getCategory().getName(), Collectors.counting())
            );
}
