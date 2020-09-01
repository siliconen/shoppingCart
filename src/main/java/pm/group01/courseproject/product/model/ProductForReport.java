package pm.group01.courseproject.product.model;

import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductForReport {
    private Integer productID;
    private String name;
    private double price;
    private String description;

    private Integer categoryId;
    private String categoryName;
}
