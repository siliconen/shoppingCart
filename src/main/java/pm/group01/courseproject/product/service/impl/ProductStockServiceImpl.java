package pm.group01.courseproject.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pm.group01.courseproject.product.model.ProductStock;
import pm.group01.courseproject.product.repository.ProductStockRepository;
import pm.group01.courseproject.product.service.ProductStockService;

import java.util.List;

/*
 * @author Battuguldur Ganbold (986874)
 */

@Service
public class ProductStockServiceImpl implements ProductStockService {

    @Autowired
    private ProductStockRepository productStockRepository;

    @Override
    public ProductStock save(ProductStock productStock) {
        return productStockRepository.save(productStock);
    }

    @Override
    public boolean delete(Integer id) {
        productStockRepository.deleteById(id);
        return true;
    }

    @Override
    public ProductStock update(ProductStock productStock) {
        return productStockRepository.save(productStock);
    }

    @Override
    public List<ProductStock> findAll() {
        return productStockRepository.findAll();
    }

    @Override
    public ProductStock findById(Integer id) {
        return productStockRepository.findById(id).orElse(null);
    }

    @Override
    public boolean delete(ProductStock productStock) {
        productStockRepository.delete(productStock);
        return true;
    }

    @Override
    public ProductStock findByProductId(Integer productId) {
        return productStockRepository.findAllByProduct_ProductID(productId);
    }
}
