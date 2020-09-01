package pm.group01.courseproject.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
/*
 * Authored with surgical percison by Islam Ahmad
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartToOrderDTO {
    List<CartLineForOrderDTO> shoppinCartToOrderDTOList;
    Integer endUserId;
}
