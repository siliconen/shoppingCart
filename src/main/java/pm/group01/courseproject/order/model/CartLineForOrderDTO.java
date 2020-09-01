package pm.group01.courseproject.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
/*
 * Authored with surgical percison by Islam Ahmad
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CartLineForOrderDTO {
    Long productId;
    Integer quantity;
    String productDesc;
    double price;
    Integer sellerId;
}
