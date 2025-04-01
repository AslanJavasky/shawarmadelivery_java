package com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.adapter;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.UUIDUtils;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.MenuItemNeo4jRepository;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.OrderNeo4jRepository;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.UserNeo4jRepository;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.entity.MenuItemEntity;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.entity.OrderEntity;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.entity.UserEntity;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.entity.mapper.OrderMapper;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.entity.mapper.UserMapper;
import com.aslanjavasky.shawarmadelviry.domain.model.IOrder;
import com.aslanjavasky.shawarmadelviry.domain.model.IUser;
import com.aslanjavasky.shawarmadelviry.domain.model.OrderStatus;
import com.aslanjavasky.shawarmadelviry.domain.repo.OrderRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("OrderRepoAdapter_Neo4j")
public class OrderRepoAdapter implements OrderRepo {

    private final OrderNeo4jRepository orderRepository;
    private final UserNeo4jRepository userRepository;
    private final MenuItemNeo4jRepository menuItemRepository;
    private final OrderMapper orderMapper;
    private final UserMapper userMapper;

    public OrderRepoAdapter(OrderNeo4jRepository orderRepository, UserNeo4jRepository userRepository, MenuItemNeo4jRepository menuItemRepository,
                            @Qualifier("OrderM_Neo4j") OrderMapper orderMapper,
                            @Qualifier("UserM_Neo4j") UserMapper userMapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.menuItemRepository = menuItemRepository;
        this.orderMapper = orderMapper;
        this.userMapper = userMapper;
    }

    @Override
    public IOrder saveOrder(IOrder order) {
        return orderMapper.getIOrderFromOrderEntity(orderRepository.save(
                orderMapper.getOrderEntityFromIOrder(order)));

    }

    @Override
    public IOrder updateOrder(IOrder order) {

        if (!orderRepository.existsById(UUIDUtils.getUUIDFromLong(order.getId()))) {
            throw new RuntimeException("Order not found with id: " + order.getId());
        }
//        OrderEntity existingOrderEntity = orderRepository.findById(UUIDUtils.getUUIDFromLong(order.getId()))
//                .orElseThrow(() -> new RuntimeException("Order not found with id: " + order.getId()));

//        return orderMapper.getIOrderFromOrderEntity(
//                orderRepository.save(orderMapper.getOrderEntityFromIOrder(order)));
        return saveOrder(order);
    }

    @Override
    public IOrder updateOrderStatus(Long orderId, OrderStatus status) {
        OrderEntity orderEntity = orderRepository.findById(UUIDUtils.getUUIDFromLong(orderId))
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        UserEntity userEntity = userRepository.findById(orderEntity.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + orderEntity.getUser().getId()));
        orderEntity.setStatus(status);
        orderEntity.setUser(userEntity);
        return orderMapper.getIOrderFromOrderEntity(orderRepository.save(orderEntity));
    }


    @Override
    public List<IOrder> getOrdersByUser(IUser user) {
        return orderRepository.findByUser(userMapper.getUserEntityFromIUser(user)).stream()
                .map(orderMapper::getIOrderFromOrderEntity).toList();
    }


    @Override
    public List<IOrder> getOrdersByStatus(OrderStatus orderStatus) {
        List<OrderEntity> orderEntities = orderRepository.findByStatus(orderStatus);
        return orderEntities.stream().map(orderMapper::getIOrderFromOrderEntity).toList();
    }

    public IOrder getOrderById(Long orderId) {

        OrderEntity orderEntity = orderRepository.findById(UUIDUtils.getUUIDFromLong(orderId))
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        return orderMapper.getIOrderFromOrderEntity(orderEntity);
    }
}
