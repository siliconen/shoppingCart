package pm.group01.courseproject.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pm.group01.courseproject.user.model.Category;
import pm.group01.courseproject.user.repository.UCategoryRepository;
import pm.group01.courseproject.user.service.UCategoryService;

import java.util.List;
import java.util.Optional;

@Service
public class UCategoryServiceImpl implements UCategoryService {
    @Autowired
    UCategoryRepository uCategoryRepository;

    @Override
    public List<Category> findCategorys() {
        return uCategoryRepository.findAll();
    }

    @Override
    public Optional<Category> findCategoryById(int id) {
        return uCategoryRepository.findById(id);
    }

    @Override
    public Category saveCategory(Category category) {
        return uCategoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Category category) {
         uCategoryRepository.delete(category);
    }

    @Override
    public void deleteCategoryById(Integer categoryId) {
        uCategoryRepository.deleteById(categoryId);
    }

    @Override
    public Optional<Category> findCategoryByName(String categoryName) {
        return uCategoryRepository.findAll().stream().filter(c -> c.getName().contains(categoryName)).findFirst();
    }
}
