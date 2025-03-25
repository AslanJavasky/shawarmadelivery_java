package com.aslanjavasky.shawarmadelviry.data.repoImpls.mongodb.adapter;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.UUIDUtils;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.mongodb.entity.mapper.DeliveryMapper;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.mongodb.entity.DeliveryEntity;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.mongodb.DeliveryMongoRepository;
import com.aslanjavasky.shawarmadelviry.domain.model.IDelivery;
import com.aslanjavasky.shawarmadelviry.domain.repo.DeliveryRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("DeliveryRepoAdapter_MongoDB")
public class DeliveryRepoAdapter implements DeliveryRepo {

    private final DeliveryMongoRepository deliveryRepository;
    private final DeliveryMapper mapper;
    private final OrderRepoAdapter orderRepoAdapter;

    public DeliveryRepoAdapter(
            DeliveryMongoRepository deliveryRepository,
            @Qualifier("DeliveryM_MongoDB") DeliveryMapper mapper,
            @Qualifier("OrderRepoAdapter_MongoDB") OrderRepoAdapter orderRepoAdapter) {
        this.deliveryRepository = deliveryRepository;
        this.mapper = mapper;
        this.orderRepoAdapter = orderRepoAdapter;
    }

    @Override
    public IDelivery saveDelivery(IDelivery delivery) {
        DeliveryEntity savedDelivery = deliveryRepository.save(mapper.getDeliveryEntityFromIDelivery(delivery));
        return mapper.getIDeliveryFromDeliveryEntity(savedDelivery, delivery.getOrder());
    }


    @Override
    public IDelivery updateDelivery(IDelivery delivery) {
        return saveDelivery(delivery);
    }

    @Override
    public IDelivery getDeliveryById(Long id) {
        DeliveryEntity deliveryEntity = deliveryRepository.findById(UUIDUtils.getUUIDFromLong(id))
                .orElseThrow(() -> new RuntimeException("Delivery not found with id: " + id));

        return mapper.getIDeliveryFromDeliveryEntity(
                deliveryEntity,
                orderRepoAdapter.getOrderById(deliveryEntity.getOrderId())
        );
    }


}
