package pm.group01.courseproject.payment.repository;

import org.springframework.data.repository.CrudRepository;
import pm.group01.courseproject.payment.model.PaymentTransaction;
/*
 * Authored with surgical percison by Islam Ahmad
 * */
public interface PaymentTransactionHistoryRepository extends CrudRepository<PaymentTransaction, Integer> {
}
