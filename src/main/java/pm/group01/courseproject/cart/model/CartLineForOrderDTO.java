package pm.group01.courseproject.cart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartLineForOrderDTO {
    Long productId;
    Integer quantity;
    String productDesc;
    double price;
    Integer sellerId;
}
