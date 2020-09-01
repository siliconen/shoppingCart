package pm.group01.courseproject.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
/*
 * Authored with surgical percison by Islam Ahmad
 * */
@Entity
@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderElement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer orderId;
    Integer endUserId;
    OrderStatus orderStatus;
    Integer shippingAddressId;
    Integer billingAddressId;
    Double orderTotal;
    Integer sellerId;
    @OneToOne(mappedBy = "orderClass", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    OrderLine orderLine;

}