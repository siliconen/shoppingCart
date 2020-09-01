package pm.group01.courseproject.product.model;

import lombok.*;

@Data
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductForCartLineDTO {
    Integer productId;
    String productDesc;
    Integer sellerId;
    double price;
}
