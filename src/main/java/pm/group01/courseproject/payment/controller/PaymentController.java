package pm.group01.courseproject.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pm.group01.courseproject.payment.model.Payment;
import pm.group01.courseproject.payment.model.PaymentType;
import pm.group01.courseproject.payment.service.PaymentService;

import java.util.Optional;
/*
 * Authored with surgical percison by Islam Ahmad
 * */
@RequestMapping("/payment")
@RestController
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @PostMapping("getpaymentid/")
    public Integer getPaymentId(@RequestBody Payment payment) {
        Optional<Payment> retrievedPayment = paymentService.findByCardNoAndCvvAndExpiryDateAndPaymentTypeAndNameOnCard(payment.getCardNo(), payment.getCvv(), payment.getExpiryDate(), payment.getPaymentType(), payment.getNameOnCard());
        if (retrievedPayment.isPresent()) return retrievedPayment.get().getPaymentId();
        else return -1;
    }

    //    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("/charge/{amount}")
    public Boolean charge(@RequestBody Payment payment, @PathVariable Double amount) {
        Integer paymentId;
        if (payment.getPaymentId() == null) paymentId = getPaymentId(payment);
        else paymentId = payment.getPaymentId();
//        System.out.println("1-payment sent has/found paymentID " + paymentId);
//        System.out.println("2-receieved this payment in request body" + payment);
        if (paymentId < 1) return false;
        if (paymentService.checkPayment(payment, paymentId)) {
//            System.out.println("check payment passed");
            Optional<Payment> retrieved = paymentService.findByIdAndType(paymentId, payment.getPaymentType());
            if (retrieved.isPresent()) {
//                System.out.println("3-retrieved payment sent has paymentID " + retrieved.get().getPaymentId());
                Payment existingCard = retrieved.get();
                Double cardBalance = existingCard.getBalance();
//                System.out.println("4-existing card" + existingCard);
                if (cardBalance < amount) return false;
                existingCard.setBalance(cardBalance - amount);
                paymentService.save(existingCard);
                paymentService.savePaymentransaction(retrieved.get().getPaymentId(), retrieved.get().getPaymentType(), amount);
                return true;
            }
        }
        return false;
    }


//    @GetMapping("/pay/{paymentId}/{amount}")
//    public boolean pay(@PathVariable Integer paymentId, @PathVariable double amount) {
//        return paymentService.pay(paymentId, amount);
//    }
//    /*for data entry purposes only, no GUI usage*/
//    @PostMapping("/add")
//    public boolean addPayment(@RequestBody Payment payment) {
//        Optional<Payment> retrieved = paymentService.find(payment);
//        if (retrieved.isPresent()) {
//            Payment saved = paymentService.save(payment);
//            return true;
//        }
//        return false;
//    }

//    @PutMapping("/update")
//    public boolean updatePayment(@RequestBody Payment payment) {
//        Optional<Payment> retrieved = paymentService.findById(payment.getPaymentId());
//        if (retrieved.isPresent()) {
//            Payment saved = paymentService.save(retrieved.get());
//            return true;
//        } else return false;
//    }

//    @DeleteMapping("/delete")
//    public void deletePayment(@RequestBody Integer paymentId) {
//        paymentService.deleteById(paymentId);
//    }

    @GetMapping("/create")
    public void createCards() {
        Payment p1 = new Payment(PaymentType.AMEX, "123456789012345", "11/24", "1234", 1000.00, "user's name");
        Payment p2 = new Payment(PaymentType.VC, "1234567890123456", "11/25", "567", 100.00, "user's name");
        Payment p3 = new Payment(PaymentType.MC, "6543210987654321", "11/26", "890", 5000.00, "user's name");
        paymentService.save(p1);
        paymentService.save(p2);
        paymentService.save(p3);
    }
}