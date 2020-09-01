package pm.group01.courseproject.user.service;

import org.springframework.stereotype.Service;
import pm.group01.courseproject.user.model.Product;
import pm.group01.courseproject.user.model.Seller;

import java.util.List;
import java.util.Optional;

@Service
public interface SellerService {
    Optional<Seller> findSellerById(Integer id);

    List<Seller> findASellers();

    Seller saveSeller(Seller seller);

    boolean deleteSellerById(Integer sellerId);

    List<Seller> findSellersByName(String sellerName);

    List<Product> getProductBySellerId(Integer id);

    Seller updateSeller(Seller seller);

    public Seller updateSellerPassword(Integer id, String password);


}
