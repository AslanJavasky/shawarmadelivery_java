package com.aslanjavasky.shawarmadelviry.data.repoImpls.collectionFrw;

import com.aslanjavasky.shawarmadelviry.domain.model.*;
import com.aslanjavasky.shawarmadelviry.domain.repo.OrderRepo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class OrderRepoImpl implements OrderRepo {

    private final List<IOrder> orders=new ArrayList<>();
    private final AtomicLong nextId=new AtomicLong(1);

    @Override
    public IOrder saveOrder(IOrder order) {
        order.setId(nextId.getAndIncrement());
        orders.add(order);
        return order;
    }

    @Override
    public IOrder updateOrder(IOrder order) {
        int index=orders.indexOf(order);
        if (index != -1) orders.set(index, order);
        return order;
    }

    private IOrder getOrderById(Long id) {
        return orders.stream()
                .filter(order -> order.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public IOrder updateOrderStatus(Long orderId, OrderStatus status) {
        var order=getOrderById(orderId);
        order.setStatus(status);
        updateOrder(order);
        return order;
    }

    @Override
    public List<IOrder> getOrdersByUser(IUser user) {
        return orders.stream()
                .filter(order -> order.getUser().getId().equals(user.getId()))
                .collect(Collectors.toList());
//                .toList();
    }

    @Override
    public List<IOrder> getOrdersByStatus(OrderStatus orderStatus) {
        return orders.stream()
                .filter(order -> order.getStatus().name().equals(orderStatus.name()))
                .toList();
    }
}
