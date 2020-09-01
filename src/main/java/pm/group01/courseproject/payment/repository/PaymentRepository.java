package pm.group01.courseproject.payment.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pm.group01.courseproject.payment.model.Payment;
import pm.group01.courseproject.payment.model.PaymentType;

import java.util.Optional;
/*
 * Authored with surgical percison by Islam Ahmad
 * */
@Repository
public interface PaymentRepository extends CrudRepository<Payment, Integer> {
    Optional<Payment> findPaymentByPaymentIdAndPaymentType(Integer paymentId, PaymentType paymentType);

    Optional<Payment> findByCardNoAndCvvAndExpiryDateAndPaymentTypeAndNameOnCard(String cardNo, String cvv, String expiryDate, PaymentType paymentType, String nameOnCard);

    Optional<Payment> findByPaymentId(Integer paymentId);

}
