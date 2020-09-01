package pm.group01.courseproject.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pm.group01.courseproject.securityconfig.jwt.util.JwtUtil;
import pm.group01.courseproject.user.model.Product;
import pm.group01.courseproject.user.model.Seller;
import pm.group01.courseproject.user.service.SellerService;
import pm.group01.courseproject.user.service.UProductService;
import springfox.documentation.spring.web.json.Json;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    private SellerService sellerService;
    @Autowired
    private UProductService UProductService;
    @Autowired
    private JwtUtil jwtTokenUtil;
    String token = "";


    @PreAuthorize("hasAuthority('SELLER')")
    @GetMapping("/")
    public Optional<Seller> getSellerById(@RequestHeader(name = "Authorization") String token) {
        token = token.replaceAll("Bearer", "").trim();
        Integer sellerid = Integer.parseInt(jwtTokenUtil.extractId(token));
        return sellerService.findSellerById(sellerid);
    }

    @PreAuthorize("hasAuthority('SELLER')")
    @GetMapping("/getProducts")
    public List<Product> getAllProducts(@RequestHeader(name = "Authorization") String token) {
        token = token.replaceAll("Bearer", "").trim();
        Integer sellerid = Integer.parseInt(jwtTokenUtil.extractId(token));

        return sellerService.getProductBySellerId(sellerid);

    }


    @PreAuthorize("hasAnyAuthority('SELLER','ADMIN')")
    @PostMapping
    public Seller saveSeller(@RequestBody Seller seller) {
        return sellerService.saveSeller(seller);
    }

    @PreAuthorize("hasAnyAuthority('SELLER','ADMIN')")
    @PutMapping
    public Seller updateSeller(@RequestBody Seller seller, @RequestHeader(name = "Authorization") String token) {
        token = token.replaceAll("Bearer", "").trim();
        Integer sellerid = Integer.parseInt(jwtTokenUtil.extractId(token));
        Seller dbSeller = sellerService.findSellerById(sellerid).get();
        dbSeller.setName(seller.getName());
        dbSeller.setEmail(seller.getEmail());
        dbSeller.setPassword(seller.getPassword());
        return sellerService.saveSeller(dbSeller);
    }

    @PreAuthorize("hasAuthority('SELLER')")
    @PostMapping("/product")
    public Product createProduct(@RequestBody Product product) {
        /*assuming that the product has all required info*/
        product.setStatus(false);
        return UProductService.saveProduct(product);
    }

    @PreAuthorize("hasAuthority('SELLER')")
    @DeleteMapping("/product/{productId}")
    public Json deleteProduct(@PathVariable Integer productId) {
        /*assuming that the product just added by mistake not revied or approved*/
        Product p = UProductService.findProductById(productId).get();
        UProductService.deleteProduct(p);
        return new Json("success");
    }

    @PreAuthorize("hasAuthority('SELLER')")
    @PostMapping("/product/edit")
    public Product editProduct(@RequestBody Product product, @RequestHeader(name = "Authorization") String token) {
        token = token.replaceAll("Bearer", "").trim();
        Integer sellerid = Integer.parseInt(jwtTokenUtil.extractId(token));
        Optional<Product> optionalProduct = UProductService.findProductById(product.getProductID());
        if (optionalProduct.isPresent()) {
            /*assuming that the product has all required info*/
            if (optionalProduct.get().getSeller().getId() == sellerid)
                return UProductService.saveProduct(product);
            return product;
        } else return product;
    }

    @GetMapping("/create")
    public Seller create() {
        Seller seller = new Seller();
        seller.setName("Islam");
        seller.setEmail("e@m.com");
        seller.setUsername("Islam");
        seller.setPassword("pass");
        return sellerService.saveSeller(seller);
    }
}
