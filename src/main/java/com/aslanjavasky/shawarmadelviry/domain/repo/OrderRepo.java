package com.aslanjavasky.shawarmadelviry.domain.repo;

import com.aslanjavasky.shawarmadelviry.domain.model.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public interface OrderRepo {
    IOrder saveOrder(IOrder order);
    IOrder updateOrder(IOrder order);
    IOrder updateOrderStatus(Long orderId, OrderStatus status);
//    Order getOrderById(Long id);
    List<IOrder> getOrdersByUser(IUser user);
    List<IOrder> getOrdersByStatus(OrderStatus orderStatus);
}
