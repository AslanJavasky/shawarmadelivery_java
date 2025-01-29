package com.aslanjavasky.shawarmadelviry.domain.interractor;

import com.aslanjavasky.shawarmadelviry.domain.model.*;
import com.aslanjavasky.shawarmadelviry.domain.repo.OrderRepo;
import java.util.List;

public class OrderInterractor {

    private final OrderRepo repo;

    public OrderInterractor(OrderRepo repo) {
        this.repo = repo;
    }

    public IOrder createOrder(IOrder order) {
        return repo.saveOrder(order);
    }

    public IOrder changeOrder(IOrder order) {
        return repo.updateOrder(order);
    }

    public IOrder updateOrderStatus(Long orderId, OrderStatus status) {
        return repo.updateOrderStatus(orderId, status);
    }

    public List<IOrder> getOrdersByUser(IUser user) {
        return repo.getOrdersByUser(user);
    }

    public List<IOrder> getOrdersByStatus(OrderStatus status) {
        return repo.getOrdersByStatus(status);
    }

}
