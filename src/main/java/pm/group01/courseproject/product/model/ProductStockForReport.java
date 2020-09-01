package pm.group01.courseproject.product.model;

import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductStockForReport {
    private Integer stockId;
    private Integer productID;
    private String name;
    private double price;
    private Integer quantity;
    private String description;

    private Integer categoryId;
    private String categoryName;
    private Integer sellerId;
    private String sellerName;
}
