package pm.group01.courseproject.user.service;


import org.springframework.stereotype.Service;
import pm.group01.courseproject.user.model.BrandOwner;
import pm.group01.courseproject.user.model.Product;

import java.util.List;
import java.util.Optional;

@Service
public interface BrandOwnerService {
    List<BrandOwner> findBrandOwners();
    Optional<BrandOwner> findBrandOwnerById(int id);
    BrandOwner saveBrandOwner(BrandOwner brandOwner);
    BrandOwner editBrandOwnerNotChangePass(BrandOwner brandOwner);
    boolean deleteBrandOwner(BrandOwner brandOwner);
    boolean deleteBrandOwnerById(Integer brandOwnerId);
    Optional<BrandOwner> findBrandOwnerByName(String brandOwnerName);
    List<Product>listAllPendingApproval(Boolean status,Integer brandOwner_id);
}
