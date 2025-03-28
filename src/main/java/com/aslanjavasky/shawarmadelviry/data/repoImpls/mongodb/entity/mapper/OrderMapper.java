//package com.aslanjavasky.shawarmadelviry.data.repoImpls.mongodb.entity.mapper;
//
//import com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.UUIDUtils;
//import com.aslanjavasky.shawarmadelviry.data.repoImpls.mongodb.entity.OrderEntity;
//import com.aslanjavasky.shawarmadelviry.domain.model.IMenuItem;
//import com.aslanjavasky.shawarmadelviry.domain.model.IOrder;
//import com.aslanjavasky.shawarmadelviry.domain.model.IUser;
//import com.aslanjavasky.shawarmadelviry.domain.model.Order;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Component;
//import java.util.List;
//
//@Component("OrderM_MongoDB")
//public class OrderMapper {
//
//    private final MenuItemMapper menuItemMapper;
//
//    public OrderMapper(ModelMapper modelMapper,
//                       @Qualifier("MenuItemM_MongoDB") MenuItemMapper menuItemMapper) {
//        this.menuItemMapper = menuItemMapper;
//    }
//
//    public OrderEntity getOrderEntityFromIOrder(IOrder iOrder) {
//
//        if (iOrder == null) return null;
//
//        return new OrderEntity(
//                UUIDUtils.getUUIDFromLong((iOrder.getId())),
//                iOrder.getDateTime(),
//                iOrder.getStatus(),
//                UUIDUtils.getUUIDFromLong(iOrder.getUser().getId()),
//                iOrder.getTotalPrice(),
//                iOrder.getItemList()
//                        .stream()
//                        .map(item -> UUIDUtils.getUUIDFromLong(item.getId()))
//                        .toList()
//        );
//    }
//
//    public IOrder getIOrderFromOrderEntity(OrderEntity orderEntity, IUser user, List<IMenuItem> itemList) {
//
//        if (orderEntity == null) return null;
//        return new Order(
//                UUIDUtils.getLongFromUUID(orderEntity.getId()),
//                orderEntity.getDateTime(),
//                orderEntity.getStatus(),
//                user,
//                itemList,
//                orderEntity.getTotalPrice()
//        );
//    }
//
//}
