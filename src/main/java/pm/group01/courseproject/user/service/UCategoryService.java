package pm.group01.courseproject.user.service;

import org.springframework.stereotype.Service;
import pm.group01.courseproject.user.model.Category;

import java.util.List;
import java.util.Optional;

@Service
public interface UCategoryService {
    List<Category> findCategorys();

    Optional<Category> findCategoryById(int id);

    Category saveCategory(Category Category);

    void deleteCategory(Category Category);

    void deleteCategoryById(Integer CategoryId);

    Optional<Category> findCategoryByName(String CategoryName);

}
