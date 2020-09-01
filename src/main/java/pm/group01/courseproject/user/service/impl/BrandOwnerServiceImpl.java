package pm.group01.courseproject.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pm.group01.courseproject.user.model.Brand;
import pm.group01.courseproject.user.model.BrandOwner;
import pm.group01.courseproject.user.model.Product;
import pm.group01.courseproject.user.repository.BrandOwnerRepository;
import pm.group01.courseproject.user.repository.UProductRepository;
import pm.group01.courseproject.user.service.BrandOwnerService;
import pm.group01.courseproject.user.service.UBrandService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BrandOwnerServiceImpl implements BrandOwnerService {
    @Autowired
    BrandOwnerRepository brandOwnerRepository;

    @Autowired
    UProductRepository uProductRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UBrandService ubrandService;

    @Override
    public List<BrandOwner> findBrandOwners() {
        return brandOwnerRepository.findAll();
    }

    @Override
    public Optional findBrandOwnerById(int id) {
        return brandOwnerRepository.findById(id);
    }

    @Override
    public BrandOwner saveBrandOwner(BrandOwner brandOwner) {
        Brand b = brandOwner.getBrand();
        if (b != null) ubrandService.saveBrand(b);
        brandOwner.setPassword(passwordEncoder.encode(brandOwner.getPassword()));
        return brandOwnerRepository.save(brandOwner);
    }

    @Override
    public BrandOwner editBrandOwnerNotChangePass(BrandOwner brandOwner) {
        Brand b = brandOwner.getBrand();
        if (b != null) ubrandService.saveBrand(b);
        //brandOwner.setPassword(passwordEncoder.encode(brandOwner.getPassword()));
        String password = brandOwner.getPassword();
        if (password != null) {
            String encodedPassword = passwordEncoder.encode(brandOwner.getPassword());
            String dbPassword = brandOwnerRepository.findById(brandOwner.getId()).get().getPassword();
            if (!dbPassword.equals(encodedPassword) && !dbPassword.equals(password))
                brandOwner.setPassword(encodedPassword);
        }
        return brandOwnerRepository.save(brandOwner);
    }

    @Override
    public boolean deleteBrandOwner(BrandOwner brandOwner) {
        try {
            brandOwnerRepository.delete(brandOwner);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public boolean deleteBrandOwnerById(Integer brandOwnerId) {
        try {
            brandOwnerRepository.deleteById(brandOwnerId);
            ;
            return true;
        } catch (Exception ex) {
            return false;
        }
    }


    @Override
    public Optional<BrandOwner> findBrandOwnerByName(String brandOwnerName) {
        List<BrandOwner> BrandOwnerList = brandOwnerRepository.findAll();
        BrandOwnerList.stream().filter(c -> c.getName().equalsIgnoreCase(brandOwnerName)).findFirst();
        return BrandOwnerList.stream().filter(c -> c.getName().equalsIgnoreCase(brandOwnerName)).findFirst();
    }


    @Override
    public List<Product> listAllPendingApproval(Boolean status, Integer brandOwner_id) {
        BrandOwner brandOwner = brandOwnerRepository.findById(brandOwner_id).get();
        List<Product> list = uProductRepository.findAllByStatus(status);
        List<Product> result = list.stream()
                .filter(p -> p.getBrand().getId().equals(brandOwner.getBrand().getId()))
                .collect(Collectors.toList());
        return result;
    }

}
