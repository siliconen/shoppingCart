package pm.group01.courseproject.product.integrations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import pm.group01.courseproject.order.event.ProductSoldEvent;
import pm.group01.courseproject.product.model.*;
import pm.group01.courseproject.product.repository.*;
import pm.group01.courseproject.product.service.ProductService;
import pm.group01.courseproject.product.service.ProductStockService;
import pm.group01.courseproject.product.service.ProductStockTxnService;

import javax.transaction.Transactional;
import java.time.LocalDate;

/*
 * @author Islam and Battuguldur Ganbold (986874)
 */

@Service
@EnableAsync
@Transactional
public class ProductSoldEventListener {

    @Autowired
    private ProductStockTxnService productStockTxnService;

    @Autowired
    private ProductStockService productStockService;

    @Autowired
    private ProductService productService;

    @Async
    @EventListener
    public void onProductSoldEvent(ProductSoldEvent productSoldEvent){
        /*adjust stock based on productID and quantity in the event*/

        /*
        ProductSoldEvent
        private Long productID;
        private int quantity;
        private Integer sellerId;
        */
        System.out.println("onProductSoldEvent -- Start --");
        System.out.println(productSoldEvent.toString());
        Product product = productService.findById(productSoldEvent.getProductID().intValue());
        if(product == null){
            System.out.println("This product is not found.");
            return;
        }
        ProductStock productStock = productStockService.findByProductId(productSoldEvent.getProductID().intValue());
        Integer quantity = productStock.getQuantity() - productSoldEvent.getQuantity();
        if(quantity < 0){
            System.out.println("This product is sold.");
            return;
        }
        productStock.setQuantity(quantity);
        productStockService.save(productStock);

        ProductStockTxn productStockTxn = new ProductStockTxn();
        productStockTxn.setProduct(product);
        productStockTxn.setSellerId(productSoldEvent.getSellerId());
        productStockTxn.setQuantity(productSoldEvent.getQuantity() * (-1));
        productStockTxn.setTxnDate(LocalDate.now());
        productStockTxn = productStockTxnService.save(productStockTxn);

        System.out.println("onProductSoldEvent -- Finished --");
    }
}
