package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.mapper;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.entity.MenuItemEntity;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.entity.OrderEntity;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.entity.UserEntity;
import com.aslanjavasky.shawarmadelviry.domain.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class OrderMapper {

    private final ModelMapper modelMapper;

    public OrderMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public OrderEntity getOrderEntityFromIOrder(IOrder iOrder) {

        if (iOrder == null) return null;
        OrderEntity orderEntity = modelMapper.map(iOrder, OrderEntity.class);
        orderEntity.setUserId(iOrder.getUser().getId());
        return orderEntity;

//        return new OrderEntity(
//                iOrder.getId(),
//                iOrder.getDateTime(),
//                iOrder.getStatus(),
//                iOrder.getUser().getId(),
//                iOrder.getTotalPrice()
//        );
    }

    public IOrder getIOrderFromOrderEntity(OrderEntity orderEntity, IUser user, List<IMenuItem> menuItems) {

        if (orderEntity == null) return null;
        IOrder order = new Order();
        order = modelMapper.map(orderEntity, Order.class);
        order.setUser(user);
        order.setItemList(menuItems);
        return order;
//        return new Order(
//                orderEntity.getId(),
//                orderEntity.getDateTime(),
//                orderEntity.getStatus(),
//                user,
//                menuItems,
//                orderEntity.getTotalPrice()
//        );
    }
}
