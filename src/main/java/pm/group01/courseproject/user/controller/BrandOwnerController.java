package pm.group01.courseproject.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pm.group01.courseproject.securityconfig.jwt.util.JwtUtil;
import org.springframework.web.client.RestOperations;
import pm.group01.courseproject.user.integrations.UserDomainService;
import pm.group01.courseproject.user.model.BrandOwner;
import pm.group01.courseproject.user.model.Product;
import pm.group01.courseproject.user.model.ProductForProductDTO;
import pm.group01.courseproject.user.service.BrandOwnerService;
import pm.group01.courseproject.product.model.ProductFromUserDTO;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/brandowner")
public class BrandOwnerController {
    @Autowired
    private JwtUtil jwtTokenUtil;
    String token = "";

    @Autowired UserDomainService userDomainService;
    @Autowired private BrandOwnerService brandOwnerService;
    @Value("${productUrl}") String productUrl;
    @Value("${categoryUrl}") String categoryUrl;
    @Value("${brandUrl}") String brandUrl;
    @Autowired RestOperations restClient;

    @PreAuthorize("hasAuthority('BRANDOWNER')")
    @GetMapping("/{brandOwnerId}")
    public Optional<BrandOwner> getBrandOwner(@PathVariable Integer brandOwnerId) {
        return brandOwnerService.findBrandOwnerById(brandOwnerId);
    }

    @PreAuthorize("hasAuthority('BRANDOWNER')")
    @PutMapping
    public BrandOwner updateBrandOwner(@RequestBody BrandOwner brandOwner) {
        return brandOwnerService.saveBrandOwner(brandOwner);
    }


    @PreAuthorize("hasAuthority('BRANDOWNER')")
    @PutMapping("/editProfile")
    public BrandOwner updateBrandOwner_NoChangePass(@RequestBody BrandOwner brandOwner) {
        return brandOwnerService.editBrandOwnerNotChangePass(brandOwner);
    }

    @GetMapping("/create")
    public BrandOwner create() {
        BrandOwner brandOwner = new BrandOwner();
        brandOwner.setName("Islam");
        brandOwner.setEmail("e@m.com");
        brandOwner.setUsername("Islam");
        brandOwner.setPassword("pass");
        return brandOwnerService.saveBrandOwner(brandOwner);
    }

    @PreAuthorize("hasAuthority('BRANDOWNER')")
    @PostMapping("/reviewproduct/{decision}")
    public ProductForProductDTO reviewProduct(@RequestBody Product productFromUserDTO, @PathVariable boolean decision, @RequestHeader(name = "Authorization") String token) {
        ProductForProductDTO productToReturn =null;
        if (decision) {
            ProductFromUserDTO response = restClient.postForObject(productUrl+"createProduct/", productFromUserDTO, ProductFromUserDTO.class);
            productToReturn= userDomainService.approveProduct(productFromUserDTO, decision);
        }/*return productDTO*/
        return productToReturn;
    }

    @PreAuthorize("hasAuthority('BRANDOWNER')")
    @GetMapping("/getAllPendingApproval")
    public List<pm.group01.courseproject.user.model.Product>getAllPending(@RequestHeader(name = "Authorization") String token)
    {
        token = token.replaceAll("Bearer", "").trim();
        Integer brandOwner_id =  Integer.parseInt(jwtTokenUtil.extractId(token));
        return brandOwnerService.listAllPendingApproval(false,brandOwner_id);
    }

    @PreAuthorize("hasAuthority('BRANDOWNER')")
    @GetMapping("/getAllApprovedProducts")
    public List<Product>getAllApproved( @RequestHeader(name = "Authorization") String token)
    {
        token = token.replaceAll("Bearer", "").trim();
        Integer brandOwner_id =  Integer.parseInt(jwtTokenUtil.extractId(token));
        return brandOwnerService.listAllPendingApproval(true,brandOwner_id);
    }
}
