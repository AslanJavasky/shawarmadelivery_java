package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.mapper;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.entity.DeliveryEntity;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.entity.UserEntity;
import com.aslanjavasky.shawarmadelviry.domain.model.*;
import org.springframework.stereotype.Component;

@Component
public class DeliveryMapper {

    public DeliveryEntity getDeliveryEntityFromIDelivery(IDelivery iDelivery) {
        return new DeliveryEntity(
                iDelivery.getId(),
                iDelivery.getAddress(),
                iDelivery.getPhone(),
                iDelivery.getDateTime(),
                iDelivery.getOrder().getId()
        );
    }

    public IDelivery getIDeliveryFromDeliveryEntity(DeliveryEntity deliveryEntity, IOrder iorder) {
        return new Delivery(
                deliveryEntity.getId(),
                deliveryEntity.getAddress(),
                deliveryEntity.getPhone(),
                deliveryEntity.getDateTime(),
                iorder
        );
    }
}
