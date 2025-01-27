package com.aslanjavasky.shawarmadelviry.data.repoImpls.collectionFrw;

import com.aslanjavasky.shawarmadelviry.domain.model.Order;
import com.aslanjavasky.shawarmadelviry.domain.model.OrderStatus;
import com.aslanjavasky.shawarmadelviry.domain.model.User;
import com.aslanjavasky.shawarmadelviry.domain.repo.OrderRepo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class OrderRepoImpl implements OrderRepo {

    private final List<Order> orders=new ArrayList<>();
    private final AtomicLong nextId=new AtomicLong(1);

    @Override
    public Order saveOrder(Order order) {
        order.setId(nextId.getAndIncrement());
        orders.add(order);
        return order;
    }

    @Override
    public Order updateOrder(Order order) {
        int index=orders.indexOf(order);
        if (index != -1) orders.set(index, order);
        return order;
    }

    private Order getOrderById(Long id) {
        return orders.stream()
                .filter(order -> order.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        var order=getOrderById(orderId);
        order.setStatus(status);
        updateOrder(order);
        return order;
    }

    @Override
    public List<Order> getOrdersByUser(User user) {
        return orders.stream()
                .filter(order -> order.getUser().getId().equals(user.getId()))
                .collect(Collectors.toList());
//                .toList();
    }

    @Override
    public List<Order> getOrdersByStatus(OrderStatus orderStatus) {
        return orders.stream()
                .filter(order -> order.getStatus().name().equals(orderStatus.name()))
                .toList();
    }
}
