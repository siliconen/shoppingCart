package pm.group01.courseproject.cart.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductForCartLineDTO {
    Integer productId;
    String productDesc;
    double price;
}