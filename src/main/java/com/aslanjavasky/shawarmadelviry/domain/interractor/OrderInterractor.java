package com.aslanjavasky.shawarmadelviry.domain.interractor;

import com.aslanjavasky.shawarmadelviry.domain.model.Order;
import com.aslanjavasky.shawarmadelviry.domain.model.OrderStatus;
import com.aslanjavasky.shawarmadelviry.domain.model.User;
import com.aslanjavasky.shawarmadelviry.domain.repo.OrderRepo;

import java.util.List;

public class OrderInterractor {

    private final OrderRepo repo;

    public OrderInterractor(OrderRepo repo) {
        this.repo = repo;
    }

    public Order createOrder(Order order) {
        return repo.saveOrder(order);
    }

    public Order changeOrder(Order order) {
        return repo.updateOrder(order);
    }

    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        return repo.updateOrderStatus(orderId, status);
    }

    public List<Order> getOrdersByUser(User user) {
        return repo.getOrdersByUser(user);
    }

    public List<Order> getOrdersByStatus(OrderStatus status) {
        return repo.getOrdersByStatus(status);
    }

}
