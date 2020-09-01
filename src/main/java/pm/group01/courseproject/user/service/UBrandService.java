package pm.group01.courseproject.user.service;

import org.springframework.stereotype.Service;
import pm.group01.courseproject.user.model.Brand;

import java.util.List;
import java.util.Optional;

@Service
public interface UBrandService {
    List<Brand> findBrands();
    Optional<Brand> findBrandById(int id);
    Brand saveBrand(Brand brand);
    boolean deleteBrand(Brand brand);
    boolean deleteBrandById(Integer brandId);
    Optional<Brand> findBrandByName(String brandName);
    public List<Brand> findAllBySomeFields(String code, String name);
}
