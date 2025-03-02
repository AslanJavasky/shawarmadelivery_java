package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.pagingAndSortingRepository.adapter;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.OrderEntity;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.mapper.MenuItemMapper;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.mapper.OrderMapper;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.mapper.UserMapper;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.pagingAndSortingRepository.MenuItemPSRepository;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.pagingAndSortingRepository.OrderPSRepository;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.pagingAndSortingRepository.UserPSRepository;
import com.aslanjavasky.shawarmadelviry.domain.model.IMenuItem;
import com.aslanjavasky.shawarmadelviry.domain.model.IOrder;
import com.aslanjavasky.shawarmadelviry.domain.model.IUser;
import com.aslanjavasky.shawarmadelviry.domain.model.OrderStatus;
import com.aslanjavasky.shawarmadelviry.domain.repo.OrderRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.List;

@Component("OrderRepoAdapter_PageSortING")
public class OrderRepoAdapter implements OrderRepo {

    private final OrderPSRepository orderRepository;
    private final UserPSRepository userRepository;
    private final MenuItemPSRepository menuItemRepository;

    private final OrderMapper orderMapper;
    private final UserMapper userMapper;
    private final MenuItemMapper menuItemMapper;

    public OrderRepoAdapter(OrderPSRepository orderRepository,
                            UserPSRepository userRepository,
                            MenuItemPSRepository menuItemRepository,
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
//        List<OrderEntity> orderEntities = orderRepository.getByStatus(orderStatus);
//
//        return orderEntities.stream().map(orderEntity -> {
//            return getOrderById(orderEntity.getId());
//        }).toList();
        Sort sort=Sort.by(Sort.Direction.DESC, "date_time");
        return getAOrdersByStatusOrderByDateTimeDesc(orderStatus,sort );

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

    public List<IOrder> getAllOrders(Sort sort) {
        return orderRepository.findAll(sort).stream().map(orderEntity -> {
            return getOrderById(orderEntity.getId());
        }).toList();
    }

    public Page<IOrder> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable).map(orderEntity -> {
            return getOrderById(orderEntity.getId());
        });
    }

    public Page<IOrder> getAOrdersByUser(IUser user, Pageable pageable) {
        return orderRepository.findByUserId(user.getId(), pageable).map(orderEntity -> {
            return getOrderById(orderEntity.getId());
        });
    }

    public Page<IOrder> getAOrdersByStatus(OrderStatus status, Pageable pageable) {
        return orderRepository.findByStatus(status, pageable).map(orderEntity -> {
            return getOrderById(orderEntity.getId());
        });
    }

    public List<IOrder> getAOrdersByStatusOrderByDateTimeDesc(OrderStatus status, Sort sort) {
        return orderRepository.findByStatusOrderByDateTimeDesc(status, sort).stream()
                .map(orderEntity -> {
                    return getOrderById(orderEntity.getId());
                }).toList();
    }

    public Page<IOrder> getOrdersByUserAndStatus(IUser user, OrderStatus status, Pageable pageable) {
        return orderRepository.findByUserIdAndStatus(user.getId(), status, pageable)
                .map(orderEntity -> {
                    return getOrderById(orderEntity.getId());
                });
    }

    public Page<IOrder> getOrdersByTotalPriceGTE(BigDecimal totalPrice, Pageable pageable) {
        return orderRepository.findByTotalPriceGreaterThanEqual(totalPrice, pageable)
                .map(orderEntity -> {
                    return getOrderById(orderEntity.getId());
                });
    }

    public List<IOrder> getOrdersByTotalPriceLTE(BigDecimal totalPrice, Sort sort) {
        return orderRepository.findByTotalPriceLessThanEqualOrderByDateTimeAsc(totalPrice, sort)
                .stream()
                .map(orderEntity -> {
                    return getOrderById(orderEntity.getId());
                })
                .toList();
    }
}