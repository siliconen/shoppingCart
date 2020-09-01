package pm.group01.courseproject.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pm.group01.courseproject.order.model.OrderElement;

import java.util.List;
import java.util.Optional;
/*
 * Authored with surgical percison by Islam Ahmad
 * */
@Repository
public interface OrderRepository extends JpaRepository<OrderElement, Integer> {

    @Query("select o from OrderElement o where o.orderId= ?1 and o.endUserId = ?2")
    Optional<OrderElement> findByIdAndUserId(Integer orderId, Integer userId);

    List<OrderElement> findAllBySellerId(Integer sellerId);

    List<OrderElement> findAllByEndUserId(Integer endUserId);

    OrderElement findOrderElementByOrderIdAndSellerId(Integer orderId, Integer sellerId);

}
