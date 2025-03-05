package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity.mapper;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity.MenuItemEntity;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity.OrderEntity;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity.UserEntity;
import com.aslanjavasky.shawarmadelviry.domain.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import java.util.List;

@Component("OrderM_JPA")
public class OrderMapper {

    private final ModelMapper modelMapper;
    private final MenuItemMapper menuItemMapper;

    public OrderMapper(ModelMapper modelMapper, MenuItemMapper menuItemMapper) {
        this.modelMapper = modelMapper;
        this.menuItemMapper = menuItemMapper;
    }

    public OrderEntity getOrderEntityFromIOrder(IOrder iOrder) {

        if (iOrder == null) return null;
        OrderEntity orderEntity = modelMapper.map(iOrder, OrderEntity.class);
        orderEntity.setUser((UserEntity) iOrder.getUser());
//        List<MenuItemEntity> cartEntity = iOrder.getItemList().stream()
//                .map(menuItemMapper::getMenuItemEntityFromIMenuItem)
//                .toList();
//        orderEntity.setItemList(cartEntity);
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
        IOrder order = modelMapper.map(orderEntity, Order.class);
        order.setUser(orderEntity.getUser());
//        List<IMenuItem> cartDomain = orderEntity.getItemList().stream()
//                .map(menuItemMapper::getIMenuItemFromMenuItemEntity)
//                .toList();
//        order.setItemList(cartDomain);
        return order;

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
