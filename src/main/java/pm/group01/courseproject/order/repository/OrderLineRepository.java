package pm.group01.courseproject.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pm.group01.courseproject.order.model.OrderLine;

import java.util.List;
/*
 * Authored with surgical percison by Islam Ahmad
 * */
@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, Integer> {

    List<OrderLine> findAllBySellerId(Integer sellerId);
}
