package com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.entity.mapper;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.UUIDUtils;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.entity.DeliveryEntity;
import com.aslanjavasky.shawarmadelviry.domain.model.Delivery;
import com.aslanjavasky.shawarmadelviry.domain.model.IDelivery;
import com.aslanjavasky.shawarmadelviry.domain.model.IOrder;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("DeliveryM_Cassandra")
public class DeliveryMapper {


    public DeliveryEntity getDeliveryEntityFromIDelivery(IDelivery iDelivery) {

        if (iDelivery == null) return null;

        return new DeliveryEntity(
                UUIDUtils.getUUIDFromLong(iDelivery.getId()),
                iDelivery.getAddress(),
                iDelivery.getPhone(),
                iDelivery.getDateTime(),
                UUIDUtils.getUUIDFromLong(iDelivery.getOrder().getId())
        );
    }

    public IDelivery getIDeliveryFromDeliveryEntity(DeliveryEntity deliveryEntity, IOrder iorder) {

        if (deliveryEntity == null) return null;

        return new Delivery(
                deliveryEntity.getId().getMostSignificantBits(),
                deliveryEntity.getAddress(),
                deliveryEntity.getPhone(),
                deliveryEntity.getDateTime(),
                iorder
        );
    }
}
