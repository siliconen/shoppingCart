package pm.group01.courseproject.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pm.group01.courseproject.user.model.Brand;
import pm.group01.courseproject.user.repository.UBrandRepository;
import pm.group01.courseproject.user.service.UBrandService;

import java.util.List;
import java.util.Optional;

@Service
public class UBrandServiceImpl implements UBrandService {
    @Autowired
    private UBrandRepository UBrandRepository;

    @Override
    public List<Brand> findBrands() {
        return UBrandRepository.findAll();
    }

    @Override
    public Optional<Brand> findBrandById(int id) {
        return UBrandRepository.findById(id);
    }

    @Override
    public Brand saveBrand(Brand brand) {
        return UBrandRepository.save(brand);
    }

    @Override
    public boolean deleteBrand(Brand brand) {
        UBrandRepository.delete(brand);
        return true;
    }

    @Override
    public boolean deleteBrandById(Integer brandId) {
        UBrandRepository.deleteById(brandId);
        return true;
    }

    @Override
    public Optional<Brand> findBrandByName(String brandName) {
        return null;
    }

    @Override
    public List<Brand> findAllBySomeFields(String code, String name) {
        return UBrandRepository.findAllBySomeFields(code, name);
    }
}
