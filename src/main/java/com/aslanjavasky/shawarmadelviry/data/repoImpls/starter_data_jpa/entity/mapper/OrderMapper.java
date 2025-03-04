package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity.mapper;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity.OrderEntity;
import com.aslanjavasky.shawarmadelviry.domain.model.IOrder;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

//@Component
public class OrderMapper {

    private final ModelMapper modelMapper;

    public OrderMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public OrderEntity getOrderEntityFromIOrder(IOrder iOrder) {

        if (iOrder == null) return null;
        OrderEntity orderEntity = modelMapper.map(iOrder, OrderEntity.class);
//        orderEntity.setUserId(iOrder.getUser().getId());
        return orderEntity;

//        return new OrderEntity(
//                iOrder.getId(),
//                iOrder.getDateTime(),
//                iOrder.getStatus(),
//                iOrder.getUser().getId(),
//                iOrder.getTotalPrice()
//        );
    }

    public IOrder getIOrderFromOrderEntity(OrderEntity orderEntity) {

        if (orderEntity == null) return null;
        return modelMapper.map(orderEntity, IOrder.class);

//        return new Order(
//                orderEntity.getId(),
//                orderEntity.getDateTime(),
//                orderEntity.getStatus(),
//                orderEntity.getUser(),
//                orderEntity.getMenuItems(),
//                orderEntity.getTotalPrice()
//        );
    }
}
