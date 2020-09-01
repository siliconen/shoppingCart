package pm.group01.courseproject.cart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartUiToFrontEnd {
    List<CartLineUi>cartLineUiList;
    Integer user_id;


}