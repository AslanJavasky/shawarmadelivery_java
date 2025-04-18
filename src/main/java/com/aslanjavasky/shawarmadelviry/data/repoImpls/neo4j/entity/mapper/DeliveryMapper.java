//package com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.entity.mapper;
//
//import com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.UUIDUtils;
//import com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.entity.DeliveryEntity;
//import com.aslanjavasky.shawarmadelviry.domain.model.Delivery;
//import com.aslanjavasky.shawarmadelviry.domain.model.IDelivery;
//import com.aslanjavasky.shawarmadelviry.domain.model.IOrder;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Component;
//
//@Component("DeliveryM_Neo4j")
//public class DeliveryMapper {
//
//    private final OrderMapper orderMapper;
//
//    public DeliveryMapper(@Qualifier("OrderM_Neo4j") OrderMapper orderMapper) {
//        this.orderMapper = orderMapper;
//    }
//
//    public DeliveryEntity getDeliveryEntityFromIDelivery(IDelivery iDelivery) {
//
//        if (iDelivery == null) return null;
//
//        return new DeliveryEntity(
//                UUIDUtils.getUUIDFromLong(iDelivery.getId()),
//                iDelivery.getAddress(),
//                iDelivery.getPhone(),
//                iDelivery.getDateTime(),
//                orderMapper.getOrderEntityFromIOrder(iDelivery.getOrder())
//        );
//    }
//
//    public IDelivery getIDeliveryFromDeliveryEntity(DeliveryEntity deliveryEntity) {
//
//        if (deliveryEntity == null) return null;
//
//        return new Delivery(
//                deliveryEntity.getId().getMostSignificantBits(),
//                deliveryEntity.getAddress(),
//                deliveryEntity.getPhone(),
//                deliveryEntity.getDateTime(),
//                orderMapper.getIOrderFromOrderEntity(deliveryEntity.getOrder())
//        );
//    }
//}
