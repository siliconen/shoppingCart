package pm.group01.courseproject.product.model;

import lombok.*;
import pm.group01.courseproject.user.model.Seller;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductFromUserDTO {
    private Integer productID;
    private String name;
    private String description;
    private Brand brand;
    private double price;
    private boolean status;
    private CategoryFromUserDTO category;
    private String imageLink;
    private Seller seller;
}
