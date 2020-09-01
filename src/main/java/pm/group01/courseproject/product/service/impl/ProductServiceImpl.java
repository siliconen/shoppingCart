package pm.group01.courseproject.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pm.group01.courseproject.product.model.Product;
import pm.group01.courseproject.product.repository.ProductRepository;
import pm.group01.courseproject.product.service.ProductService;

import java.util.List;

/*
 * @author Battuguldur Ganbold (986874)
 */

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public boolean delete(Integer id) {
        productRepository.deleteById(id);
        return true;
    }

    @Override
    public Product update(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(Integer id) {
        return productRepository.findById(id).orElse(null);
    }



    @Override
    public List<Product> findAllBySomeFields(String name, String desc) {
        return productRepository.findAllBySomeFields(name, desc);
    }

    @Override
    public List<Product> findAllBySomeFieldsWithPrice(String name, String desc, double startPrice, double endPrice) {
        return productRepository.findAllBySomeFieldsWithPrice(name, desc, startPrice, endPrice);
    }

    @Override
    public List<Product> findByCategoryIs(Integer categoryId) {
        return productRepository.findAllByCategoryIs(categoryId);
    }

    @Override
    public List<Product> findByProductsInCategory(Integer categoryId, String name, String desc) {
        return productRepository.findByProductsInCategory(categoryId, name, desc);
    }

    @Override
    public List<Product> findByProductsInCategoryWithPrice(Integer categoryId, String name, String desc, double startPrice, double endPrice) {
        return productRepository.findByProductsInCategoryWithPrice(categoryId, name, desc, startPrice, endPrice);
    }
}
