package com.aslanjavasky.shawarmadelviry.data.repoImpls.redis.adapter;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.UUIDUtils;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.redis.entity.OrderEntity;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.redis.entity.mapper.MenuItemMapper;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.redis.entity.mapper.OrderMapper;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.redis.entity.mapper.UserMapper;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.redis.MenuItemRedisRepository;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.redis.OrderRedisRepository;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.redis.UserRedisRepository;
import com.aslanjavasky.shawarmadelviry.domain.model.IMenuItem;
import com.aslanjavasky.shawarmadelviry.domain.model.IOrder;
import com.aslanjavasky.shawarmadelviry.domain.model.IUser;
import com.aslanjavasky.shawarmadelviry.domain.model.OrderStatus;
import com.aslanjavasky.shawarmadelviry.domain.repo.OrderRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Component("OrderRepoAdapter_Redis")
public class OrderRepoAdapter implements OrderRepo {

    private final OrderRedisRepository orderRepository;
    private final UserRedisRepository userRepository;
    private final MenuItemRedisRepository menuItemRepository;
    private final OrderMapper orderMapper;
    private final UserMapper userMapper;
    private final MenuItemMapper menuItemMapper;

    public OrderRepoAdapter(
            OrderRedisRepository orderRepository,
            UserRedisRepository userRepository,
            MenuItemRedisRepository menuItemRepository,
            @Qualifier("OrderM_Redis")  OrderMapper orderMapper,
            @Qualifier("UserM_Redis") UserMapper userMapper,
            @Qualifier("MenuItemM_Redis") MenuItemMapper menuItemMapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.menuItemRepository = menuItemRepository;
        this.orderMapper = orderMapper;
        this.userMapper = userMapper;
        this.menuItemMapper = menuItemMapper;
    }

    @Override
    public IOrder saveOrder(IOrder order) {
        OrderEntity orderEntity = orderRepository.save(orderMapper.getOrderEntityFromIOrder(order));
        return getOrderById(orderEntity.getId());
    }


    @Override
    public IOrder updateOrder(IOrder order) {
        OrderEntity orderEntityForSaving = orderMapper.getOrderEntityFromIOrder(order);
        OrderEntity existingOrderEntity = getOrderEntityByUUID(orderEntityForSaving.getId());
        OrderEntity savedOrderEntity = orderRepository.save(orderEntityForSaving);
        return getOrderById(savedOrderEntity.getId());
    }


    @Override
    public IOrder updateOrderStatus(Long orderId, OrderStatus status) {

        OrderEntity orderEntity = getOrderEntityByUUID(UUIDUtils.getUUIDFromLong(orderId));
        orderEntity.setStatus(status);
        OrderEntity savedOrderEntity = orderRepository.save(orderEntity);
        return getOrderById(savedOrderEntity.getId());
    }


    @Override
    public List<IOrder> getOrdersByUser(IUser user) {

        UUID userId = UUIDUtils.getUUIDFromLong(user.getId());
        List<OrderEntity> ordersEntity = orderRepository.findByUserId(userId);
        return ordersEntity.stream().map(entity -> getOrderById(entity.getId())).toList();

    }


    @Override
    public List<IOrder> getOrdersByStatus(OrderStatus orderStatus) {
        List<OrderEntity> orderEntities = orderRepository.findByStatus(orderStatus);
        return orderEntities
                .stream()
                .sorted(Comparator.comparing(OrderEntity::getDateTime).reversed())
                .map(orderEntity -> getOrderById(orderEntity.getId()))
                .toList();
    }


    public IOrder getOrderById(UUID orderId) {

        OrderEntity orderEntity = getOrderEntityByUUID(orderId);

        IUser user = userMapper.getIUserFromUserEntity(
                userRepository.findById(orderEntity.getUserId())
                        .orElseThrow(() -> new RuntimeException("User not found with id: " + orderEntity.getUserId())));

        List<IMenuItem> menuItems = orderEntity.getMenuItemIds().stream()
                .map(menuItemId ->
                        menuItemRepository.findById(menuItemId)
                                .map(menuItemMapper::getIMenuItemFromMenuItemEntity)
                                .orElseThrow(() -> new RuntimeException("Menuitem not found with id: " + menuItemId)))
                .toList();

        return orderMapper.getIOrderFromOrderEntity(orderEntity, user, menuItems);
    }

    private OrderEntity getOrderEntityByUUID(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
    }

}
