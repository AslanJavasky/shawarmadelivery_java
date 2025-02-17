package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.mapper;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.entity.MenuItemEntity;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.entity.OrderEntity;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.entity.UserEntity;
import com.aslanjavasky.shawarmadelviry.domain.model.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class OrderMapper {

    public OrderEntity getOrderEntityFromIOrder(IOrder iOrder) {

        return new OrderEntity(
                iOrder.getId(),
                iOrder.getDateTime(),
                iOrder.getStatus(),
                iOrder.getUser().getId(),
                iOrder.getTotalPrice()
        );
    }

    public IOrder getIOrderFromOrderEntity(OrderEntity orderEntity, IUser user, List<IMenuItem> menuItems) {
        return new Order(
                orderEntity.getId(),
                orderEntity.getDateTime(),
                orderEntity.getStatus(),
                user,
                menuItems,
                orderEntity.getTotalPrice()
        );
    }
}
