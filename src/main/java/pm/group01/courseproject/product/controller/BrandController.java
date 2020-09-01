package pm.group01.courseproject.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pm.group01.courseproject.product.model.Brand;
import pm.group01.courseproject.product.service.BrandService;

import java.util.List;
import java.util.Optional;

/*
 * @author Battuguldur Ganbold (986874)
 */

@RestController
@RequestMapping("/brand/api/v1")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("/")
    public ResponseEntity<?> findAll(){
        List<Brand> brands = brandService.findBrands();
        return ResponseEntity.ok(brands);
    }

    @GetMapping("/{brandid}")
    public ResponseEntity<?> findById(@PathVariable("brandid") Integer brandid){
        return Optional
                .ofNullable( brandService.findBrandById(brandid) )
                .map( p -> ResponseEntity.ok().body(p) )
                .orElseGet( () -> ResponseEntity.notFound().build() );
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody Brand brand){
        Brand res = brandService.saveBrand(brand);
        return ResponseEntity.ok(res);
    }

    @PutMapping("/")
    public ResponseEntity<?> update(@RequestBody Brand brand){
        Brand res = brandService.saveBrand(brand);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id){
        Brand res = brandService.findBrandById(id).orElse(null);
        if(res == null)
            return new ResponseEntity<Brand>(res, HttpStatus.NOT_FOUND);

        brandService.deleteBrandById(id);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchProducts(
            @RequestParam(value = "code", required = false, defaultValue = "") String name,
            @RequestParam(value = "name", required = false, defaultValue = "") String desc
    ){
        return Optional
                .ofNullable( brandService.findAllBySomeFields(
                        name.toLowerCase(),
                        desc.toLowerCase()
                ) )
                .map( p -> ResponseEntity.ok().body(p) )
                .orElseGet( () -> ResponseEntity.notFound().build() );
    }
}
