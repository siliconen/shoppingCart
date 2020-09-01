package pm.group01.courseproject.order.service;

import pm.group01.courseproject.order.model.OrderElement;
/*
 * Authored with surgical percison by Islam Ahmad
 * */
public interface OrderService {
    OrderElement findById(Integer orderId);
    OrderElement save(OrderElement order);

    OrderElement findByIdAndSellerId(Integer orderId, Integer sellerId);
    OrderElement findByIdAndUserId(Integer orderId, Integer userId);
}

