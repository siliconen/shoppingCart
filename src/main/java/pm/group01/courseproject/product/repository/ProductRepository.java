package pm.group01.courseproject.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pm.group01.courseproject.product.model.Category;
import pm.group01.courseproject.product.model.Product;

import java.util.List;

/*
 * @author Battuguldur Ganbold (986874)
 */

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findAllByCategoryIs(Integer categoryId);
    List<Product> findAllBySellerId(Integer sellerId);

    @Query(value = "select p.* " +
            " from Product p " +
            " where lower(p.name) LIKE %:name% and lower(p.description) LIKE %:desc% "
            , nativeQuery = true)
    List<Product> findAllBySomeFields(@Param("name") String name,
                                      @Param("desc") String desc);

    @Query(value = "select p.* " +
            " from Product p " +
            " where lower(p.name) LIKE %:name% and lower(p.description) LIKE %:desc% " +
            " and p.price between :startPrice and :endPrice ", nativeQuery = true)
    List<Product> findAllBySomeFieldsWithPrice(@Param("name") String name,
                                               @Param("desc") String desc,
                                               @Param("startPrice") double startPrice,
                                               @Param("endPrice") double endPrice);

    @Query(value = " select distinct c.category_id, c.name " +
            " from Product p " +
            " left join Category c on c.category_id=p.category_id " +
            " where lower(p.name) LIKE %:name% and lower(p.description) LIKE %:desc% " +
            " group by c.category_id, c.name "
            , nativeQuery = true)
    List<Category> findByNameDescDistCategory(@Param("name") String name, @Param("desc") String desc);

    @Query(value = "select p.* " +
            " from Product p " +
            " where p.category_id = :categoryId and lower(p.name) LIKE %:name% and lower(p.description) LIKE %:desc%",
            nativeQuery = true)
    List<Product> findByProductsInCategory(@Param("categoryId") Integer categoryId,
                                           @Param("name") String name,
                                           @Param("desc") String desc);

    @Query(value = "select p.* " +
            " from Product p " +
            " where p.category_id = :categoryId and lower(p.name) LIKE %:name% and lower(p.description) LIKE %:desc%" +
            " and p.price between :startPrice and :endPrice  ", nativeQuery = true)
    List<Product> findByProductsInCategoryWithPrice(@Param("categoryId") Integer categoryId,
                                                    @Param("name") String name,
                                                    @Param("desc") String desc,
                                                    @Param("startPrice") double startPrice,
                                                    @Param("endPrice") double endPrice);
}
