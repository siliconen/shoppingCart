package pm.group01.courseproject.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pm.group01.courseproject.product.model.*;
import pm.group01.courseproject.product.service.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/*
 * @author Battuguldur Ganbold (986874)
 */

@RestController
@RequestMapping("/products/api/v1")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductStockService productStockService;

    @Autowired
    private ProductStockTxnService productStockTxnService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;

    @GetMapping(value = {"/", ""})
    public ResponseEntity<List<Product>> getAll(){
        List<Product> productList = productService.findAll();
        return ResponseEntity.ok(productList);
    }

    @GetMapping("/{productid}")
    public ResponseEntity<ProductStock> getById(@PathVariable("productid") Integer id){
        return Optional
                .ofNullable( productStockService.findByProductId(id) )
                .map( p -> ResponseEntity.ok().body(p) )
                .orElseGet( () -> ResponseEntity.notFound().build() );
    }

    @PreAuthorize("hasAuthority('SELLER')")
    @PostMapping("/")
    public ResponseEntity<Product> create(@RequestBody Product product){
        Product res = productService.save(product);
        return ResponseEntity.ok(res);
    }

    @PreAuthorize("hasAnyAuthority('SELLER','ADMIN','BRANDOWNER')")
    @PutMapping("/")
    public ResponseEntity<Product> update(@RequestBody Product product){
        Integer id = product.getProductID();
        Product res = productService.findById(id);
        if(res == null)
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        res = productService.save(product);
        return ResponseEntity.ok(res);
    }

    @PreAuthorize("hasAnyAuthority('SELLER','ADMIN','BRANDOWNER')")
    @DeleteMapping("/{productid}")
    public ResponseEntity<Product> delete(@PathVariable("productid") Integer id){
        Product res = productService.findById(id);
        if(res == null)
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);

        productService.delete(id);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{categoryid}/products")
    public ResponseEntity<List<Product>> getByCategoryId(@PathVariable("categoryid") Integer categoryid){
        return Optional
                .ofNullable( productService.findByCategoryIs(categoryid) )
                .map( p -> ResponseEntity.ok().body(p) )
                .orElseGet( () -> ResponseEntity.notFound().build() );
    }

    @GetMapping("/search")
    public ResponseEntity<ProductAndCategoryForUI> searchProducts(
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "desc", required = false, defaultValue = "") String desc,
            @RequestParam(value = "startPrice", required = false, defaultValue = "0") double startPrice,
            @RequestParam(value = "endPrice", required = false, defaultValue = "0") double endPrice
    ){

        ProductAndCategoryForUI res = new ProductAndCategoryForUI();
        List<Product> products = new ArrayList<>();

        if(endPrice == 0)
            products = productService.findAllBySomeFields(
                    name.toLowerCase(),
                    desc.toLowerCase()
            );
        else
            products = productService.findAllBySomeFieldsWithPrice(
                                            name.toLowerCase(),
                                            desc.toLowerCase(),
                                            startPrice,
                                            endPrice
                                    );
        Set<Category> categories = new HashSet<>();
        for (Product p: products) {
            categories.add(p.getCategory());
        }

        res.setProducts(products);
        res.setCategories(categories);

        return ResponseEntity.ok(res);
    }

    @GetMapping("/{categoryid}/search")
    public ResponseEntity<List<Product>> getByProductsInCategory(
            @PathVariable("categoryid") Integer categoryid,
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "desc", required = false, defaultValue = "") String desc,
            @RequestParam(value = "startPrice", required = false, defaultValue = "0") double startPrice,
            @RequestParam(value = "endPrice", required = false, defaultValue = "0") double endPrice
    ){
        List<Product> list = new ArrayList<>();

        if(endPrice == 0)
            list = productService.findByProductsInCategory(
                    categoryid,
                    name.toLowerCase(),
                    desc.toLowerCase()
            );
        else
            list = productService.findByProductsInCategoryWithPrice(
                    categoryid,
                    name.toLowerCase(),
                    desc.toLowerCase(),
                    startPrice,
                    endPrice
            );

        return Optional
                .ofNullable( list )
                .map( p -> ResponseEntity.ok().body(p) )
                .orElseGet( () -> ResponseEntity.notFound().build() );
    }

    @GetMapping("/groupingByCategory")
    public ResponseEntity<Map<String, Long>> groupingByCategory(Model model) {
        List<Product> productList = productService.findAll();
        Map<String, Long> res = productService.getProductCountByCategoryName.apply(productList);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{categoryid}/searchFromCart")
    public ResponseEntity<?> getProductsForCartLine(
            @PathVariable("categoryid") Integer categoryid,
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "desc", required = false, defaultValue = "") String desc
    ) {
        List<ProductForCartLineDTO> productDTOList = productService.findByProductsInCategory(categoryid, name.toLowerCase(), desc.toLowerCase()).stream()
                .map(p -> new ProductForCartLineDTO(
                        p.getProductID(),
                        p.getDescription(),
                        p.getSellerId(),
                        p.getPrice()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(productDTOList);
    }

//    @PreAuthorize("hasAnyAuthority('SELLER', 'BRANDOWNER')")
    @PostMapping("/createProduct")
    public ProductFromUserDTO createProduct(@RequestBody ProductFromUserDTO productFromUserDTO){

        Product newProduct = new Product();
        newProduct.setProductID(productFromUserDTO.getProductID());
        newProduct.setName(productFromUserDTO.getName());
        newProduct.setDescription(productFromUserDTO.getDescription());
        newProduct.setPrice(productFromUserDTO.getPrice());
        newProduct.setImageLink(productFromUserDTO.getImageLink());
        newProduct.setSellerId(productFromUserDTO.getSeller().getId());

        Brand brand = new Brand();
        brand.setId(productFromUserDTO.getBrand().getId());
        brand.setCode(productFromUserDTO.getBrand().getCode());
        brand.setName(productFromUserDTO.getBrand().getName());
        brandService.saveBrand(brand);

        newProduct.setBrandId(productFromUserDTO.getBrand().getId());

        Category category = new Category();
        category.setCategoryId(productFromUserDTO.getCategory().getCategoryId());
        category.setName(productFromUserDTO.getCategory().getName());
        categoryService.save(category);

        newProduct.setCategory(category);
        newProduct = productService.save(newProduct);

        Integer quantity = 0;
        ProductStock reProductStock = productStockService.findByProductId(newProduct.getProductID());
        if(reProductStock == null){
            ProductStock productStock = new ProductStock();
            productStock.setProduct(newProduct);
            productStock.setQuantity(quantity);
            productStockService.save(productStock);
        }
        else {
            reProductStock.setQuantity(reProductStock.getQuantity() + quantity);
            productStockService.save(reProductStock);
        }

//        ProductStockTxn productStockTxn = new ProductStockTxn();
//        productStockTxn.setTxnDate(LocalDate.now());
//        productStockTxn.setQuantity(quantity);
//        productStockTxn.setProduct(newProduct);
//        productStockTxn.setSellerId(productFromUserDTO.getSellerId());
//        productStockTxnService.save(productStockTxn);

        return productFromUserDTO;
    }

//    @GetMapping("/getAllProducts")
//    public ResponseEntity<?> getProductsForCartLine() {
//        List<ProductFromUserDTO> productDTOList = productService.findAll().stream()
//                .map(p -> new ProductFromUserDTO(
//                        p.getProductID(),
//                        p.getName(),
//                        p.getDescription(),
//                        p.getPrice(),
//                        p.getCategory(),
//                        p.getImageLink()
//                ))
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(productDTOList);
//    }
//
//    @GetMapping("/getProduct/{id}")
//    public ResponseEntity<?> getProductsForUserDTO(@PathVariable("id") Integer id) {
//        Product p = productService.findById(id);
//        if(p == null)
//            return new ResponseEntity<>(p, HttpStatus.NOT_FOUND);
//
//        ProductFromUserDTO productForUserDTO = new ProductFromUserDTO(
//                p.getProductID(),
//                p.getName(),
//                p.getDescription(),
//                p.getPrice(),
//                p.getCategory(),
//                p.getImageLink()
//        );
//        return ResponseEntity.ok(productForUserDTO);
//    }


}
