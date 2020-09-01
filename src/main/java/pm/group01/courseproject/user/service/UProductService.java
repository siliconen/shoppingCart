package pm.group01.courseproject.user.service;

import org.springframework.stereotype.Service;
import pm.group01.courseproject.user.model.Product;

import java.util.Optional;

@Service
public interface UProductService {
    Optional<Product> findProductById(Integer Id);

    Product productApproval(Product product, Boolean decision);

    Product saveProduct(Product product);
    void deleteProduct(Product product);
    void deleteProductById(Integer id);
}
