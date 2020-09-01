package pm.group01.courseproject.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pm.group01.courseproject.user.model.Product;
import pm.group01.courseproject.user.repository.UProductRepository;
import pm.group01.courseproject.user.service.UProductService;

import java.util.Optional;

@Service
public class UProductServiceImpl implements UProductService {

    @Autowired
    UProductRepository productRepository;

    @Override
    public Optional<Product> findProductById(Integer Id) {
        return productRepository.findById(Id);
    }

    @Override
    public Product productApproval(Product product,Boolean decision) {
        product.setStatus(decision);
        return productRepository.save(product);
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

    @Override
    public void deleteProductById(Integer id) {
        productRepository.deleteById(id);
    }


}
