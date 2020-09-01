package pm.group01.courseproject.payment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
/*
 * Authored with surgical percison by Islam Ahmad
 * */
@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PaymentTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer paymentTransactionId;
    Integer paymentId;
    PaymentType paymentType;
    LocalDate timeStamp;
    Double amount;

    public PaymentTransaction(Integer paymentId, LocalDate timeStamp, PaymentType paymentType, Double amount) {
        this.paymentId = paymentId;
        this.timeStamp = timeStamp;
        this.paymentType = paymentType;
        this.amount = amount;
    }
}
