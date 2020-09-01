package pm.group01.courseproject.product.service;

import org.springframework.stereotype.Service;
import pm.group01.courseproject.user.model.Product;
import pm.group01.courseproject.user.model.ProductForProductDTO;

@Service
public interface ProductFromUserDTOService {
    Product createProductFromUProductDTP(ProductForProductDTO ppDTO);
}
