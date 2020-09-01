package pm.group01.courseproject.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pm.group01.courseproject.product.model.Category;
import pm.group01.courseproject.product.repository.CategoryRepository;
import pm.group01.courseproject.product.service.CategoryService;

import java.util.List;

/*
 * @author Battuguldur Ganbold (986874)
 */

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public boolean delete(Integer id) {
        categoryRepository.deleteById(id);
        return true;
    }

    @Override
    public Category update(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Integer id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public List<Category> getNameIsLike(String name) {
        return categoryRepository.findAllByNameIsLike(name);
    }
}
