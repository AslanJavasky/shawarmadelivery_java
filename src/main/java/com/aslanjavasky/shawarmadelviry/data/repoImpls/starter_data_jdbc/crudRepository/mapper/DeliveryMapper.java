package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.mapper;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.entity.DeliveryEntity;
import com.aslanjavasky.shawarmadelviry.domain.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DeliveryMapper {

    private final ModelMapper modelMapper;

    public DeliveryMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public DeliveryEntity getDeliveryEntityFromIDelivery(IDelivery iDelivery) {

        if (iDelivery == null) return null;

        DeliveryEntity deliveryEntity = modelMapper.map(iDelivery, DeliveryEntity.class);
        deliveryEntity.setOrderId(iDelivery.getOrder().getId());
        return deliveryEntity;
//        return new DeliveryEntity(
//                iDelivery.getId(),
//                iDelivery.getAddress(),
//                iDelivery.getPhone(),
//                iDelivery.getDateTime(),
//                iDelivery.getOrder().getId()
//        );
    }

    public IDelivery getIDeliveryFromDeliveryEntity(DeliveryEntity deliveryEntity, IOrder iorder) {

        if (deliveryEntity == null) return null;

        IDelivery delivery = new Delivery();
        delivery = modelMapper.map(deliveryEntity, Delivery.class);
        delivery.setOrder(iorder);
        return delivery;
//        return new Delivery(
//                deliveryEntity.getId(),
//                deliveryEntity.getAddress(),
//                deliveryEntity.getPhone(),
//                deliveryEntity.getDateTime(),
//                iorder
//        );
    }
}
