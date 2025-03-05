package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.adapter;


import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.DeliveryJpaRepository;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.OrderJpaRepository;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity.DeliveryEntity;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity.OrderEntity;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity.mapper.DeliveryMapper;
import com.aslanjavasky.shawarmadelviry.domain.model.IDelivery;
import com.aslanjavasky.shawarmadelviry.domain.repo.DeliveryRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Slf4j
@Component("DeliveryRepoAdapter_JPA")
public class DeliveryRepoAdapter implements DeliveryRepo {

    private final DeliveryJpaRepository deliveryRepository;
    private final OrderJpaRepository orderRepository;
    private final DeliveryMapper mapper;


    public DeliveryRepoAdapter(
            DeliveryJpaRepository deliveryRepository, OrderJpaRepository orderRepository,
            @Qualifier("DeliveryM_JPA") DeliveryMapper mapper) {
        this.deliveryRepository = deliveryRepository;
        this.orderRepository = orderRepository;
        this.mapper = mapper;
    }

    @Override
    public IDelivery saveDelivery(IDelivery delivery) {

        DeliveryEntity deliveryEntity = mapper.getDeliveryEntityFromIDelivery(delivery);

        if (deliveryEntity.getOrder() != null) {
            OrderEntity orderEntity = orderRepository.findById(delivery.getOrder().getId())
                    .orElseThrow(() -> new RuntimeException(
                            "Order not found with id:" + delivery.getOrder().getId()));
            deliveryEntity.setOrder(orderEntity);
        }

        return mapper.getIDeliveryFromDeliveryEntity(deliveryRepository.save(deliveryEntity));
    }


    @Override
    public IDelivery updateDelivery(IDelivery delivery) {
        return mapper.getIDeliveryFromDeliveryEntity(
                deliveryRepository.save(mapper.getDeliveryEntityFromIDelivery(delivery)));
    }


    @Override
    public IDelivery getDeliveryById(Long id) {
        DeliveryEntity deliveryEntity = deliveryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery not found with id: " + id));
        return mapper.getIDeliveryFromDeliveryEntity(deliveryEntity);
    }
}
