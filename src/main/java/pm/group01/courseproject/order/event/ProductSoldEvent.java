package pm.group01.courseproject.order.event;

import lombok.*;
/*
 * Authored with surgical percison by Islam Ahmad
 * */
/*this is used to impact stock in product component*/
@Data @AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class ProductSoldEvent {
    private Long productID;
    private int quantity;
    private Integer sellerId;

    @Override
    public String toString(){
        return "Product ID"+ this.productID + " sold this quantity: " + this.quantity;
    }
}
