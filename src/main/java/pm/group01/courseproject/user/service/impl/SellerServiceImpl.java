package pm.group01.courseproject.user.service.impl;

//import pm.group01.courseproject.user.repository.EndUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pm.group01.courseproject.user.model.Product;
import pm.group01.courseproject.user.model.Seller;
import pm.group01.courseproject.user.repository.SellerRepository;
import pm.group01.courseproject.user.service.SellerService;

import java.util.List;
import java.util.Optional;

@Service
public class SellerServiceImpl implements SellerService {
    @Autowired
    SellerRepository sellerRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Optional<Seller> findSellerById(Integer id) {
        return sellerRepository.findById(id);
    }





    @Override
    public List<Seller> findASellers() {
        List<Seller> s = sellerRepository.findAll();
        return sellerRepository.findAll();
    }

    @Override
    public Seller saveSeller(Seller seller) {
        seller.setPassword(passwordEncoder.encode(seller.getPassword()));
        return sellerRepository.save(seller);
    }

    @Override
    public Seller updateSeller(Seller seller) {
        String password = seller.getPassword();
        if (password != null) {
            String encodedPassword = passwordEncoder.encode(seller.getPassword());
            String dbPassword = sellerRepository.findById(seller.getId()).get().getPassword();
            if (!dbPassword.equals(encodedPassword) && !dbPassword.equals(password))
                seller.setPassword(encodedPassword);
        }
        return sellerRepository.save(seller);
    }

    @Override
    public Seller updateSellerPassword(Integer id, String password) {
        Seller seller = sellerRepository.findById(id).get();
        seller.setPassword(passwordEncoder.encode(password));
        return sellerRepository.save(seller);
    }


    @Override
    public boolean deleteSellerById(Integer sellerId) {
        try {
            sellerRepository.deleteById(sellerId);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public List<Seller> findSellersByName(String sellerName) {
        return null;
    }

    @Override
    public List<Product> getProductBySellerId(Integer id) {
        return sellerRepository.findById(id).get().getProductList();
    }
}
