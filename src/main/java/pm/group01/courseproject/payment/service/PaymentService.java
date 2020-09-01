package pm.group01.courseproject.payment.service;

import pm.group01.courseproject.payment.model.Payment;
import pm.group01.courseproject.payment.model.PaymentTransaction;
import pm.group01.courseproject.payment.model.PaymentType;

import java.util.Optional;
/*
 * Authored with surgical percison by Islam Ahmad
 * */
public interface PaymentService {
    Optional<Payment> findByIdAndType(Integer paymentID, PaymentType paymentType);

    PaymentTransaction savePaymentransaction(Integer paymentID, PaymentType paymentType, Double amount);

    Payment save(Payment payment);

    Optional<Payment> find(Payment payment);

    Optional<Payment> findById(Integer paymentId);

    Boolean checkPayment(Payment payment, Integer paymentId);

    void deleteById(Integer paymentId);

    Optional<Payment> findByCardNoAndCvvAndExpiryDateAndPaymentTypeAndNameOnCard(String cardNo, String cvv, String expiryDate, PaymentType paymentType, String nameOnCard);

    boolean pay(Integer paymentId, Double amount);
}