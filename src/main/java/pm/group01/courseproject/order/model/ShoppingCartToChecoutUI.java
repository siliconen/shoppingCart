package pm.group01.courseproject.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartToChecoutUI {
   ShoppingCartToOrderDTO shoppingCartToOrderDTO;
    Payment payment;

}
