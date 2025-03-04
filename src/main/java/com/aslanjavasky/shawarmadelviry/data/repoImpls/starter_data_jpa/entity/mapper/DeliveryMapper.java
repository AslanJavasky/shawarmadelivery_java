package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity.mapper;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity.DeliveryEntity;
import com.aslanjavasky.shawarmadelviry.domain.model.Delivery;
import com.aslanjavasky.shawarmadelviry.domain.model.IDelivery;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

//@Component
public class DeliveryMapper {

    private final ModelMapper modelMapper;

    public DeliveryMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public DeliveryEntity getDeliveryEntityFromIDelivery(IDelivery iDelivery) {

        if (iDelivery == null) return null;

        return modelMapper.map(iDelivery, DeliveryEntity.class);

//        return new DeliveryEntity(
//                iDelivery.getId(),
//                iDelivery.getAddress(),
//                iDelivery.getPhone(),
//                iDelivery.getDateTime(),
//                iDelivery.getOrder()
//        );
    }

    public IDelivery getIDeliveryFromDeliveryEntity(DeliveryEntity deliveryEntity) {

        if (deliveryEntity == null) return null;

       return modelMapper.map(deliveryEntity, Delivery.class);

//        return new Delivery(
//                deliveryEntity.getId(),
//                deliveryEntity.getAddress(),
//                deliveryEntity.getPhone(),
//                deliveryEntity.getDateTime(),
//                deliveryEntity.getOrder()
//        );
    }
}
