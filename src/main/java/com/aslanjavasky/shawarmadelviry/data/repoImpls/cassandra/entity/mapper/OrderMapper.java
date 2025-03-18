package com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.entity.mapper;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.UUIDUtils;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.entity.OrderEntity;
import com.aslanjavasky.shawarmadelviry.domain.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component("OrderM_Cassandra")
public class OrderMapper {

    private final MenuItemMapper menuItemMapper;

    public OrderMapper(ModelMapper modelMapper, MenuItemMapper menuItemMapper) {
        this.menuItemMapper = menuItemMapper;
    }

    public OrderEntity getOrderEntityFromIOrder(IOrder iOrder) {

        if (iOrder == null) return null;

        return new OrderEntity(
                UUIDUtils.getUUIDFromLong((iOrder.getId())),
                iOrder.getDateTime(),
                iOrder.getStatus(),
                UUIDUtils.getUUIDFromLong(iOrder.getUser().getId()),
                iOrder.getTotalPrice(),
                iOrder.getItemList()
                        .stream()
                        .map(item -> UUIDUtils.getUUIDFromLong(item.getId()))
                        .toList()
        );
    }

    public IOrder getIOrderFromOrderEntity(OrderEntity orderEntity, IUser user, List<IMenuItem> itemList) {

        if (orderEntity == null) return null;
        return new Order(
                UUIDUtils.getLongFromUUID(orderEntity.getId()),
                orderEntity.getDateTime(),
                orderEntity.getStatus(),
                user,
                itemList,
                orderEntity.getTotalPrice()
        );
    }

}
