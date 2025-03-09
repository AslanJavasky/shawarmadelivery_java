package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.adapter;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.DeliveryRepository;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.DeliveryEntity;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.mapper.DeliveryMapper;
import com.aslanjavasky.shawarmadelviry.domain.model.IDelivery;
import com.aslanjavasky.shawarmadelviry.domain.repo.DeliveryRepo;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Slf4j
@Component("DeliveryRepoAdapter_CRUD")
public class DeliveryRepoAdapter implements DeliveryRepo {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryMapper mapper;
    private final OrderRepoAdapter orderRepoAdapter;

    public DeliveryRepoAdapter(
            @Qualifier("DeliveryRepoExtCrudRepo") DeliveryRepository deliveryRepository,
            DeliveryMapper mapper,
            @Qualifier("OrderRepoAdapter_CRUD") OrderRepoAdapter orderRepoAdapter) {
        this.deliveryRepository = deliveryRepository;
        this.mapper = mapper;
        this.orderRepoAdapter = orderRepoAdapter;
    }

    @Transactional
    @Override
    public IDelivery saveDelivery(IDelivery delivery) {
        DeliveryEntity deliveryEntity = deliveryRepository.save(mapper.getDeliveryEntityFromIDelivery(delivery));
        return mapper.getIDeliveryFromDeliveryEntity(deliveryEntity, delivery.getOrder());
    }

    @Transactional
    @Override
    public IDelivery updateDelivery(IDelivery delivery) {
        deliveryRepository.save(mapper.getDeliveryEntityFromIDelivery(delivery));
        return delivery;
    }

    @Transactional
    @Override
    public IDelivery getDeliveryById(Long id) {
        DeliveryEntity deliveryEntity = deliveryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery not found with id: " + id));
        return mapper.getIDeliveryFromDeliveryEntity(
                deliveryEntity,
                orderRepoAdapter.getOrderById(deliveryEntity.getOrderId()));
    }
}
