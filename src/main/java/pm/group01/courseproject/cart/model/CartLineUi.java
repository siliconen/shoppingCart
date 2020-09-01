package pm.group01.courseproject.cart.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartLineUi {
    Long productId;
    Integer quantity;
    String productDesc;
    double price;
    String productName;
    Integer categoryId;
    String imageLink;
    Integer sellerId;
    String categoryName;

}
