package com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.entity.mapper;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.UUIDUtils;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.entity.OrderEntity;
import com.aslanjavasky.shawarmadelviry.domain.model.IOrder;
import com.aslanjavasky.shawarmadelviry.domain.model.Order;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("OrderM_Neo4j")
public class OrderMapper {

    private final MenuItemMapper menuItemMapper;
    private final UserMapper userMapper;

    public OrderMapper(ModelMapper modelMapper,
                       @Qualifier("MenuItemM_Neo4j") MenuItemMapper menuItemMapper,
                       @Qualifier("UserM_Neo4j")  UserMapper userMapper) {
        this.menuItemMapper = menuItemMapper;
        this.userMapper = userMapper;
    }

    public OrderEntity getOrderEntityFromIOrder(IOrder iOrder) {

        if (iOrder == null) return null;

        return new OrderEntity(
                UUIDUtils.getUUIDFromLong((iOrder.getId())),
                iOrder.getDateTime(),
                iOrder.getStatus(),
                userMapper.getUserEntityFromIUser(iOrder.getUser()),
                iOrder.getTotalPrice(),
                iOrder.getItemList().stream().map(menuItemMapper::getMenuItemEntityFromIMenuItem).toList()
        );
    }

    public IOrder getIOrderFromOrderEntity(OrderEntity orderEntity) {

        if (orderEntity == null) return null;
        return new Order(
                UUIDUtils.getLongFromUUID(orderEntity.getId()),
                orderEntity.getDateTime(),
                orderEntity.getStatus(),
                userMapper.getIUserFromUserEntity(orderEntity.getUser()),
                orderEntity.getItemList().stream().map(menuItemMapper::getIMenuItemFromMenuItemEntity).toList(),
                orderEntity.getTotalPrice()
        );
    }

}
