package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.adapter;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.MenuItemRepository;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.OrderRepository;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.UserRepository;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.entity.OrderEntity;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.mapper.MenuItemMapper;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.mapper.OrderMapper;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.mapper.UserMapper;
import com.aslanjavasky.shawarmadelviry.domain.model.IMenuItem;
import com.aslanjavasky.shawarmadelviry.domain.model.IOrder;
import com.aslanjavasky.shawarmadelviry.domain.model.IUser;
import com.aslanjavasky.shawarmadelviry.domain.model.OrderStatus;
import com.aslanjavasky.shawarmadelviry.domain.repo.OrderRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("OrderRepoAdapter_CRUD")
public class OrderRepoAdapter implements OrderRepo {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;

    private final OrderMapper orderMapper;
    private final UserMapper userMapper;
    private final MenuItemMapper menuItemMapper;

    public OrderRepoAdapter(@Qualifier("OrderRepoExtCrudRepo") OrderRepository orderRepository,
                            @Qualifier("UserRepoExtCrudRepo") UserRepository userRepository,
                            @Qualifier("MenuItemRepoExtCrudRepo") MenuItemRepository menuItemRepository,
                            OrderMapper orderMapper, UserMapper userMapper, MenuItemMapper menuItemMapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.menuItemRepository = menuItemRepository;
        this.orderMapper = orderMapper;
        this.userMapper = userMapper;
        this.menuItemMapper = menuItemMapper;
    }

    @Override
    public IOrder saveOrder(IOrder order) {
        //insert into orders
        OrderEntity savedOrder = orderRepository.save(orderMapper.getOrderEntityFromIOrder(order));
        //insert into orders_menu_items
        order.getItemList().forEach(iMenuItem -> {
            orderRepository.insertToOrdersMenuItems(savedOrder.getId(), iMenuItem.getId());
        });
        return orderMapper.getIOrderFromOrderEntity(savedOrder, order.getUser(), order.getItemList());
    }

    @Override
    public IOrder updateOrder(IOrder order) {
        OrderEntity existingOrderEntity = orderRepository.findById(order.getId())
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + order.getId()));

        OrderEntity updatedOrderEntity = orderRepository.save(orderMapper.getOrderEntityFromIOrder(order));

        orderRepository.deleteMenuItemsIdByOrderId(existingOrderEntity.getId());
        order.getItemList().forEach(menuItem -> {
            orderRepository.insertToOrdersMenuItems(existingOrderEntity.getId(), menuItem.getId());
        });

        return order;
    }

    @Override
    public IOrder updateOrderStatus(Long orderId, OrderStatus status) {
        orderRepository.updateOrderStatus(orderId, status);
        return getOrderById(orderId);
    }

    @Override
    public List<IOrder> getOrdersByUser(IUser user) {

        List<OrderEntity> orderEntities = orderRepository.getByUserId(user.getId());

        return orderEntities.stream().map(orderEntity -> {
            List<IMenuItem> menuItems = orderRepository.getMenuItemsIdsByOrderId(orderEntity.getId()).stream()
                    .map(menuItemId ->
                            menuItemRepository.findById(menuItemId)
                                    .map(menuItemMapper::getIMenuItemFromMenuItemEntity)
                                    .orElseThrow(() -> new RuntimeException("Menuitem not found with id: " + menuItemId)))

                    .toList();

            return orderMapper.getIOrderFromOrderEntity(orderEntity, user, menuItems);
        }).toList();
    }

    @Override
    public List<IOrder> getOrdersByStatus(OrderStatus orderStatus) {
        List<OrderEntity> orderEntities = orderRepository.getByStatus(orderStatus);

        return orderEntities.stream().map(orderEntity -> {
            return getOrderById(orderEntity.getId());
        }).toList();
    }

    public IOrder getOrderById(Long orderId) {

        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        IUser user = userMapper.getIUserFromUserEntity(
                userRepository.findById(orderEntity.getUserId())
                        .orElseThrow(() -> new RuntimeException("User not found with id: " + orderEntity.getUserId())));
        List<IMenuItem> menuItems = orderRepository.getMenuItemsIdsByOrderId(orderId).stream()
                .map(menuItemId ->
                        menuItemRepository.findById(menuItemId)
                                .map(menuItemMapper::getIMenuItemFromMenuItemEntity)
                                .orElseThrow(() -> new RuntimeException("Menuitem not found with id: " + menuItemId)))
                .toList();

        return orderMapper.getIOrderFromOrderEntity(orderEntity, user, menuItems);
    }
}
