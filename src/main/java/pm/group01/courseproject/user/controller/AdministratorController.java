package pm.group01.courseproject.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pm.group01.courseproject.user.model.*;
import pm.group01.courseproject.user.service.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/administrator")
public class AdministratorController {
    @Autowired
    private AdministratorService administratorService;
    @Autowired
    private BrandOwnerService brandOwnerService;
    @Autowired
    private SellerService sellerService;
    @Autowired
    private UBrandService brandService;

    @Autowired
    private UCategoryService uCategoryService;

    /*Administrator Get by Id - Create - Update */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{administratorid}")
    public Optional<Administrator> getAdministratorById(@PathVariable Integer administratorid) {
        return administratorService.findAdministratorById(administratorid);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Administrator createAdministrator(@RequestBody Administrator administrator) {
        return administratorService.saveAdministrator(administrator);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping
    public Administrator updateAdministrator(@RequestBody Administrator administrator) {
        return administratorService.saveAdministrator(administrator);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/editProfile")
    public Administrator updateAdministrator_NotChangePass(@RequestBody Administrator administrator) {
        return administratorService.saveAdministratorNotChangePass(administrator);
    }

    /*Seller Create - Update - List - Delete by Id */
    @PostMapping("/seller")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Seller createSeller(@RequestBody Seller seller) {
        return sellerService.saveSeller(seller);
    }

    @PutMapping("/seller")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Seller updateSeller(@RequestBody Seller seller) {
        return sellerService.updateSeller(seller);
    }

    @PreAuthorize("hasAnyAuthority('SELLER','ADMIN')")
    @GetMapping("/seller/{sellerid}/endpoint/{status}")
    public Seller deactivateSeller(@PathVariable boolean status, @PathVariable Integer sellerid) {

        Seller dbSeller = sellerService.findSellerById(sellerid).get();
        dbSeller.setStatus(status);
        return sellerService.updateSeller(dbSeller);
    }

    @GetMapping("/seller")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Seller> getSellers() {
        return sellerService.findASellers();
    }

    @GetMapping("/seller/{sellerid}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Optional<Seller> getSellerById(@PathVariable Integer sellerid) {
        return sellerService.findSellerById(sellerid);
    }

    @DeleteMapping("/seller/{sellerid}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public boolean deleteSeller(@PathVariable Integer sellerid) {
        return sellerService.deleteSellerById(sellerid);
    }

    /*BrandOwner Create - Update - List - Delete By Id*/
    @PostMapping("/brandowner")
    @PreAuthorize("hasAuthority('ADMIN')")
    public BrandOwner createBrandOwner(@RequestBody BrandOwner brandOwner) {
        return brandOwnerService.saveBrandOwner(brandOwner);
    }

    @PutMapping("/brandowner")
    @PreAuthorize("hasAuthority('ADMIN')")
    public BrandOwner updateBrandOwner(@RequestBody BrandOwner brandOwner) {
        return brandOwnerService.saveBrandOwner(brandOwner);
    }

    @GetMapping("/brandowner")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<BrandOwner> getBrandOwners() {
        return brandOwnerService.findBrandOwners();
    }

    @GetMapping("/brandowner/{brandOwnerid}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Optional<BrandOwner> getBrandOwnerById(@PathVariable Integer brandOwnerid) {
        return brandOwnerService.findBrandOwnerById(brandOwnerid);
    }


    @GetMapping("/category")
    @PreAuthorize("hasAnyAuthority('SELLER','ADMIN')")
    public List<Category> getCategory() {
        return uCategoryService.findCategorys();
    }

    @PostMapping("/category")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Category createCategory(@RequestBody Category category) {
        return uCategoryService.saveCategory(category);
    }
    @DeleteMapping("/category/{categoryId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public boolean deleteCategory(@PathVariable Integer categoryId) {
         uCategoryService.deleteCategoryById(categoryId);
         return true;
    }

    @PutMapping("/category")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Category updateCategory(@RequestBody Category category) {
        return uCategoryService.saveCategory(category);
    }


    @DeleteMapping("/brandowner/{brandOwnerid}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public boolean deleteBrandOwner(@PathVariable Integer brandOwnerid) {
        return brandOwnerService.deleteBrandOwnerById(brandOwnerid);
    }

    /*Brand Create - Update - List - List by*/
    @PostMapping("/brand")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Brand createBrand(@RequestBody Brand brand) {
        return brandService.saveBrand(brand);
    }

    @PutMapping("/brand")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Brand updateBrand(@RequestBody Brand brand) {
        return brandService.saveBrand(brand);
    }

    @GetMapping("/brand")
//    @PreAuthorize("hasAuthority('ADMIN')")
    @PreAuthorize("hasAnyAuthority('SELLER','ADMIN')")
    public List<Brand> getBrands() {
        return brandService.findBrands();
    }

    @GetMapping("/brand/{code}/{name}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Brand> getBrandsBy(@PathVariable String code, @PathVariable String name) {
        return brandService.findAllBySomeFields(code, name);
    }

    @DeleteMapping("/brand/{brandid}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public boolean deleteBrand(@PathVariable Integer brandid) {
        return brandService.deleteBrandById(brandid);
    }

    /*to create initial admin*/
    @GetMapping("/create")
    public Administrator create() {
        Administrator administrator = new Administrator();
        administrator.setName("Islam");
        administrator.setEmail("e@m.com");
        administrator.setUsername("Islam");
        administrator.setPassword("pass");
        return administratorService.saveAdministrator(administrator);
    }
}
