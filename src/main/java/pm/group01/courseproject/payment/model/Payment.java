package pm.group01.courseproject.payment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
/*
 * Authored with surgical percison by Islam Ahmad
 * */
@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer paymentId;
    String nameOnCard;
    PaymentType paymentType;
    @Column(unique = true)
    String cardNo;
    String expiryDate;
    String cvv;
    Double balance;

    public Payment(PaymentType paymentType, String cardNo, String expiryDate, String cvv, Double balance, String nameOnCard) {
        this.paymentType = paymentType;
        this.cardNo = cardNo;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.balance = balance;
        this.nameOnCard = nameOnCard;
    }

    public Payment(PaymentType paymentType, String cardNo, String expiryDate, String cvv, String nameOnCard) {
        this.paymentType = paymentType;
        this.cardNo = cardNo;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.nameOnCard = nameOnCard;
    }
}
