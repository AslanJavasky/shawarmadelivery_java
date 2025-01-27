package com.aslanjavasky.shawarmadelviry.domain.repo;

import com.aslanjavasky.shawarmadelviry.domain.model.Order;
import com.aslanjavasky.shawarmadelviry.domain.model.OrderStatus;
import com.aslanjavasky.shawarmadelviry.domain.model.User;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public interface OrderRepo {
    Order saveOrder(Order order);
    Order updateOrder(Order order);
    Order updateOrderStatus(Long orderId, OrderStatus status);
//    Order getOrderById(Long id);
    List<Order> getOrdersByUser(User user);
    List<Order> getOrdersByStatus(OrderStatus orderStatus);
}
