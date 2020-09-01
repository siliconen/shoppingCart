package pm.group01.courseproject.user.integrations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pm.group01.courseproject.user.model.CategoryForProductDTO;
import pm.group01.courseproject.user.model.Product;
import pm.group01.courseproject.user.model.ProductForProductDTO;
import pm.group01.courseproject.user.service.UProductService;

@Service
public class UserDomainService {

    @Autowired
    private UProductService uproductService;

    public ProductForProductDTO approveProduct(Product product, boolean approvale) {
        if (approvale) {
            try {
                product.setStatus(approvale);
                uproductService.saveProduct(product);

                ProductForProductDTO productForProductDTO = new ProductForProductDTO();
                CategoryForProductDTO c = new CategoryForProductDTO();
                c.setCategoryId(product.getCategory().getCategoryId());
                c.setName(product.getCategory().getName());

                productForProductDTO.setCategory(c);
                productForProductDTO.setBrand(product.getBrand());
                productForProductDTO.setDescription(product.getDescription());
                productForProductDTO.setImageLink(product.getImageLink());
                productForProductDTO.setName(product.getName());
                productForProductDTO.setPrice(product.getPrice());

                return productForProductDTO;
            } catch (Exception ex) {
                return null;
            }
        }
        return null;
    }


}
