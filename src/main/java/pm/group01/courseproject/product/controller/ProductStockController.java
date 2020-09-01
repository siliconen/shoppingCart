package pm.group01.courseproject.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pm.group01.courseproject.product.model.*;
import pm.group01.courseproject.product.service.*;
import pm.group01.courseproject.securityconfig.jwt.util.JwtUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/*
 * @author Battuguldur Ganbold (986874)
 */

@RestController
@RequestMapping("/stock/api/v1")
public class ProductStockController {
    @Autowired
    private ProductStockService productStockService;

    @Autowired
    private ProductStockTxnService productStockTxnService;

    @Autowired
    private ProductService productService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @PreAuthorize("hasAnyAuthority('SELLER','ADMIN','BRANDOWNER')")
    @GetMapping(value = {"/", ""})
    public ResponseEntity<?> getAll(){
        List<ProductStock> productStocks = productStockService.findAll();
        return ResponseEntity.ok(productStocks);
    }

    @PreAuthorize("hasAnyAuthority('SELLER','ADMIN','BRANDOWNER')")
    @GetMapping("/{stockid}")
    public ResponseEntity<?> getById(@PathVariable("stockid") Integer id){
        return Optional
                .ofNullable( productStockService.findById(id) )
                .map( p -> ResponseEntity.ok().body(p) )
                .orElseGet( () -> ResponseEntity.notFound().build() );
    }

    @PreAuthorize("hasAnyAuthority('SELLER','ADMIN','BRANDOWNER')")
    @GetMapping("/getProduct/{productid}")
    public ProductStock getByProductId(@PathVariable("productid") Integer id){
        return productStockService.findByProductId(id);
    }

    //new record will be create in ProductStockTxn and ProductStock
    @PreAuthorize("hasAnyAuthority('SELLER','ADMIN','BRANDOWNER')")
    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody ProductStock productStock,
                                    @RequestHeader(name = "Authorization") String token){

        token = token.replaceAll("Bearer", "").trim();
        String userId = jwtTokenUtil.extractId(token);

        ProductStockTxn productStockTxn = new ProductStockTxn();
        productStockTxn.setProduct(productStock.getProduct());
        productStockTxn.setQuantity(productStock.getQuantity());
        productStockTxn.setSellerId(Integer.parseInt(userId));
        productStockTxn.setTxnDate(LocalDate.now());
        productStockTxnService.save(productStockTxn);

        ProductStock res = productStockService.save(productStock);
        return ResponseEntity.ok(res);
    }

    //new record will be create in ProductStock and ProductStockTxn - ( newQuantity - oldQuantity  )
    @PreAuthorize("hasAnyAuthority('SELLER','ADMIN','BRANDOWNER')")
    @PutMapping("/")
    public ResponseEntity<?> update(@RequestBody ProductStock productStock,
                                    @RequestHeader(name = "Authorization") String token){
        Integer id = productStock.getStockId();
        ProductStock res = productStockService.findById(id);
        if(res == null)
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);

        token = token.replaceAll("Bearer", "").trim();
        String userId = jwtTokenUtil.extractId(token);

        ProductStockTxn productStockTxn = new ProductStockTxn();
        productStockTxn.setProduct(productStock.getProduct());
        productStockTxn.setQuantity(productStock.getQuantity() - res.getQuantity());
        productStockTxn.setSellerId(Integer.parseInt(userId));
        productStockTxn.setTxnDate(LocalDate.now());
        productStockTxnService.save(productStockTxn);

        res = productStockService.save(productStock);
        return ResponseEntity.ok(res);
    }

    @PreAuthorize("hasAnyAuthority('SELLER','ADMIN','BRANDOWNER')")
    @DeleteMapping("/{stockid}")
    public ResponseEntity<?> delete(@PathVariable("stockid") Integer id){
        ProductStock res = productStockService.findById(id);
        if(res == null)
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);

        productStockService.delete(id);
        //delete StockTxn ?
        return ResponseEntity.ok(res);
    }

    @PreAuthorize("hasAnyAuthority('SELLER','ADMIN','BRANDOWNER')")
    @DeleteMapping("/{productid}")
    public ResponseEntity<?> get(@PathVariable("productid") Integer id){
        ProductStock res = productStockService.findByProductId(id);
        return ResponseEntity.ok(res);
    }
    /*
        Checkout (If the products and quantity are available in the Products Master Table)
     */

    @GetMapping("/product/{productid}")
    public ResponseEntity<?> getStockProduct(@PathVariable("productid") Integer id){
        Integer res = 0;
        try {
            productStockService.findByProductId(id).getQuantity();
        }catch (Exception ex){

        }
        return ResponseEntity.ok(res);
    }

}
