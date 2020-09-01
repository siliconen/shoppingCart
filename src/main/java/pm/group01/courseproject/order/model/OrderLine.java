package pm.group01.courseproject.order.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
/*
 * Authored with surgical percison by Islam Ahmad
 * */
/*because we are using mySQL we must persist order lines in DB*/
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class OrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer orderLineId;
    Long productId;
    String productDesc;
    double unitPrice;
    Integer sellerId;
    int quantity;
    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "orderId")
    @Valid
    OrderElement orderClass;

    public OrderLine(Long productID, int quantity, String productDesc, double unitPrice, Integer sellerId) {
        this.productId = productID;
        this.productDesc = productDesc;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.sellerId = sellerId;
    }

    public double lineTotal() {
        return this.quantity * this.unitPrice;
    }
}
