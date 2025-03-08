package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.adapter;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.MenuItemJpaRepository;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.UserJpaRepository;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity.MenuItemEntity;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity.OrderEntity;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity.UserEntity;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity.mapper.OrderMapper;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity.mapper.UserMapper;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.OrderJpaRepository;
import com.aslanjavasky.shawarmadelviry.domain.model.IOrder;
import com.aslanjavasky.shawarmadelviry.domain.model.IUser;
import com.aslanjavasky.shawarmadelviry.domain.model.OrderStatus;
import com.aslanjavasky.shawarmadelviry.domain.repo.OrderRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("OrderRepoAdapter_JPA")
public class OrderRepoAdapter implements OrderRepo {

    private final OrderJpaRepository orderRepository;
    private final UserJpaRepository userRepository;
    private final MenuItemJpaRepository menuItemRepository;
    private final OrderMapper orderMapper;
    private final UserMapper userMapper;

    public OrderRepoAdapter(OrderJpaRepository orderRepository, UserJpaRepository userRepository, MenuItemJpaRepository menuItemRepository,
                            @Qualifier("OrderM_JPA") OrderMapper orderMapper, UserMapper userMapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.menuItemRepository = menuItemRepository;
        this.orderMapper = orderMapper;
        this.userMapper = userMapper;
    }

    @Transactional
    @Override
    public IOrder saveOrder(IOrder order) {
        OrderEntity orderEntity = orderMapper.getOrderEntityFromIOrder(order);
//        if (orderEntity.getUser() != null && orderEntity.getUser().getId() != null) {
//            UserEntity user = userRepository.findById(orderEntity.getUser().getId())
//                    .orElseThrow(() -> new RuntimeException("User not found with id: "
//                            + orderEntity.getUser().getId()));
//
//            orderEntity.setUser(user);
//        }
        List<MenuItemEntity> itemList = orderEntity.getItemList().stream()
                .map(menuItem -> menuItemRepository.findById(menuItem.getId())
                        .orElseThrow(() -> new RuntimeException("MenuItem not found with id:" + menuItem.getId())))
                .toList();
        orderEntity.setItemList(itemList);


        return orderMapper.getIOrderFromOrderEntity(orderRepository.save(orderEntity));

    }


    @Override
    public IOrder updateOrder(IOrder order) {
        OrderEntity existingOrderEntity = orderRepository.findById(order.getId())
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + order.getId()));

        return orderMapper.getIOrderFromOrderEntity(
                orderRepository.save(orderMapper.getOrderEntityFromIOrder(order)));
    }

    @Override
    public IOrder updateOrderStatus(Long orderId, OrderStatus status) {
        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        orderEntity.setStatus(status);
        return orderMapper.getIOrderFromOrderEntity(orderRepository.save(orderEntity));
    }

    @Override
    public List<IOrder> getOrdersByUser(IUser user) {
        return orderRepository.findByUser(userMapper.getUserEntityFromIUser(user)).stream()
                .map(orderMapper::getIOrderFromOrderEntity).toList();
    }

    @Override
    public List<IOrder> getOrdersByStatus(OrderStatus orderStatus) {
        List<OrderEntity> orderEntities = orderRepository.findByStatusOrderByDateTimeDesc(orderStatus);
        return orderEntities.stream().map(orderMapper::getIOrderFromOrderEntity).toList();
    }

    public IOrder getOrderById(Long orderId) {

        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        return orderMapper.getIOrderFromOrderEntity(orderEntity);
    }
}
