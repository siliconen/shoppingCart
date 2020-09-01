package pm.group01.courseproject.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pm.group01.courseproject.product.model.Brand;
import pm.group01.courseproject.product.repository.BrandRepository;
import pm.group01.courseproject.product.service.BrandService;

import java.util.List;
import java.util.Optional;

/*
 * @author Battuguldur Ganbold (986874)
 */

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandRepository brandRepository;

    @Override
    public List<Brand> findBrands() {
        return brandRepository.findAll();
    }

    @Override
    public Optional<Brand> findBrandById(int id) {
        return brandRepository.findById(id);
    }

    @Override
    public Brand saveBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public boolean deleteBrand(Brand brand) {
        brandRepository.delete(brand);
        return true;
    }

    @Override
    public boolean deleteBrandById(Integer brandId) {
        brandRepository.deleteById(brandId);
        return true;
    }

    @Override
    public Optional<Brand> findBrandByName(String brandName) {
        return null;
    }

    @Override
    public List<Brand> findAllBySomeFields(String code, String name) {
        return brandRepository.findAllBySomeFields(code, name);
    }
}
