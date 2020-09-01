package pm.group01.courseproject.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pm.group01.courseproject.product.model.ProductStockTxn;
import pm.group01.courseproject.product.repository.ProductStockTxnRepository;
import pm.group01.courseproject.product.service.ProductStockTxnService;

/*
 * @author Battuguldur Ganbold (986874)
 */

@Service
public class ProductStockTxnServiceImpl implements ProductStockTxnService {

    @Autowired
    private ProductStockTxnRepository productStockTxnRepository;

    @Override
    public ProductStockTxn save(ProductStockTxn productStockTxn) {
        return productStockTxnRepository.save(productStockTxn);
    }

}
