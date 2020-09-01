package pm.group01.courseproject.cart.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CartLine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer cartLineId;
    Integer quantity;
    Long productId;
    String productDesc;
    String productName;
    double price;
    Integer sellerId;
    Integer categoryId;
    String categoryName;
    String imageLink;
    @ManyToOne
    @JoinColumn(name = "cartId")
    @Valid @NotNull
    @JsonBackReference
    ShoppingCart cart;
}