package pm.group01.courseproject.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pm.group01.courseproject.payment.model.PaymentType;

import javax.persistence.Column;
/*
 * Authored with surgical percison by Islam Ahmad
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    Integer paymentId;
    String nameOnCard;
    PaymentType paymentType;
    String cardNo;
    String expiryDate;
    String cvv;
    Double balance;
}
