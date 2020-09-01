package pm.group01.courseproject.user.model;

import lombok.Data;


@Data
public class ProductForProductDTO {
    private Integer productID;
    private String name;
    private String description;
    private Brand brand;
    private double price;
    private boolean status;
    private CategoryForProductDTO category;
    private String imageLink;
}
