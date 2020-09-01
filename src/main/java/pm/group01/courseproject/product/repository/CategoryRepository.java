package pm.group01.courseproject.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pm.group01.courseproject.product.model.Category;

import java.util.List;

/*
 * @author Battuguldur Ganbold (986874)
 */

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    List<Category> findAllByNameIsLike(String name);

}
