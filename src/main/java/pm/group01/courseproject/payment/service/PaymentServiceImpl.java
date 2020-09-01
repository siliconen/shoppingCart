package pm.group01.courseproject.payment.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pm.group01.courseproject.payment.model.Payment;
import pm.group01.courseproject.payment.model.PaymentTransaction;
import pm.group01.courseproject.payment.model.PaymentType;
import pm.group01.courseproject.payment.repository.PaymentRepository;
import pm.group01.courseproject.payment.repository.PaymentTransactionHistoryRepository;

import java.time.LocalDate;
import java.util.Optional;
/*
 * Authored with surgical percison by Islam Ahmad
 * */
@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    PaymentTransactionHistoryRepository paymentTransactionHistoryRepository;

    public Optional<Payment> findByIdAndType(Integer paymentID, PaymentType paymentType) {
        return paymentRepository.findPaymentByPaymentIdAndPaymentType(paymentID, paymentType);
    }

    public PaymentTransaction savePaymentransaction(Integer paymentID, PaymentType paymentType, Double amount) {
        return paymentTransactionHistoryRepository.save(new PaymentTransaction(paymentID, LocalDate.now(), paymentType, amount));
    }

    public Optional<Payment> findByCardNoAndCvvAndExpiryDateAndPaymentTypeAndNameOnCard(String cardNo, String cvv, String expiryDate, PaymentType paymentType, String nameOnCard) {
        return paymentRepository.findByCardNoAndCvvAndExpiryDateAndPaymentTypeAndNameOnCard(cardNo, cvv, expiryDate, paymentType, nameOnCard);
    }

    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }

    public Optional<Payment> find(Payment payment) {
        return paymentRepository.findById(payment.getPaymentId());
    }

    @Override
    public Optional<Payment> findById(Integer paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public Boolean checkPayment(Payment payment, Integer paymentId) {
        Optional<Payment> retrievedById = paymentRepository.findById(paymentId);
//        System.out.println("in check payment, found this payment using id " + retrievedById.get());
//        System.out.println("using this payment in find by details " + payment);
        Optional<Payment> retrievedByDetails = findByCardNoAndCvvAndExpiryDateAndPaymentTypeAndNameOnCard(payment.getCardNo(), payment.getCvv(), payment.getExpiryDate(), payment.getPaymentType(), payment.getNameOnCard());
//        System.out.println("in check payment, found this payment using details in payment " + retrievedByDetails.get());
        return retrievedById.isPresent() && retrievedByDetails.isPresent()
                && retrievedByDetails.get().getPaymentId().equals(retrievedById.get().getPaymentId());
    }

    @Override
    public void deleteById(Integer paymentId) {
        paymentRepository.deleteById(paymentId);
    }

    @Override
    public boolean pay(Integer paymentId, Double amount) {
        Optional<Payment> retrievedPayment = paymentRepository.findByPaymentId(paymentId);
        return retrievedPayment.isPresent() && retrievedPayment.get().getBalance() >= amount;
    }
}
