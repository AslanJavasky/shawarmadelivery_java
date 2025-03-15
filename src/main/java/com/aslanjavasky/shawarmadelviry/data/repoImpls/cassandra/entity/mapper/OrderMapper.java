package com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.entity.mapper;

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
                getUUIDFromLong(iOrder.getId()),
                iOrder.getDateTime(),
                iOrder.getStatus(),
                getUUIDFromLong(iOrder.getUser().getId()),
                iOrder.getTotalPrice(),
                iOrder.getItemList().stream().map(item -> getUUIDFromLong(item.getId())).toList()
        );
    }

    public IOrder getIOrderFromOrderEntity(OrderEntity orderEntity, IUser user, List<IMenuItem> itemList) {

        if (orderEntity == null) return null;
        return new Order(
                orderEntity.getId().getMostSignificantBits(),
                orderEntity.getDateTime(),
                orderEntity.getStatus(),
                user,
                itemList,
                orderEntity.getTotalPrice()
        );
    }

    private UUID getUUIDFromLong(Long id) {
        Long mostSignBit = id;
        Long leastSignBit = (id << 32) | (id >>> 32);

        return id != null ? new UUID(mostSignBit, leastSignBit) : UUID.randomUUID();
    }

}
