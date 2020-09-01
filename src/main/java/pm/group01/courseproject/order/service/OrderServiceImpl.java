package pm.group01.courseproject.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pm.group01.courseproject.order.model.OrderElement;
import pm.group01.courseproject.order.repository.OrderRepository;
/*
 * Authored with surgical percison by Islam Ahmad
 * */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Override
    public OrderElement findById(Integer orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    public OrderElement findByIdAndUserId(Integer orderId, Integer userId) {
        return orderRepository.findByIdAndUserId(orderId, userId).orElse(null);
    }

    @Override
    public OrderElement save(OrderElement order) {
        return orderRepository.save(order);
    }

    @Override
    public OrderElement findByIdAndSellerId(Integer orderId, Integer sellerId) {
        return orderRepository.findOrderElementByOrderIdAndSellerId(orderId, sellerId);
    }

}

