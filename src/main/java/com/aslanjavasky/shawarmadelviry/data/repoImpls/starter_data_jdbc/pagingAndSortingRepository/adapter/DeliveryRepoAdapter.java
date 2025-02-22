package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.pagingAndSortingRepository.adapter;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.DeliveryEntity;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.mapper.DeliveryMapper;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.pagingAndSortingRepository.DeliveryPSRepository;
import com.aslanjavasky.shawarmadelviry.domain.model.IDelivery;
import java.time.LocalDateTime;
import java.util.List;
import com.aslanjavasky.shawarmadelviry.domain.model.IOrder;
import com.aslanjavasky.shawarmadelviry.domain.repo.DeliveryRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component("DeliveryRepoAdapter_PageSortING")
public class DeliveryRepoAdapter implements DeliveryRepo {

    private final DeliveryPSRepository deliveryRepository;
    private final DeliveryMapper mapper;
    private final OrderRepoAdapter orderRepoAdapter;

    public DeliveryRepoAdapter(
            DeliveryPSRepository deliveryRepository,
            DeliveryMapper mapper,
            @Qualifier("OrderRepoAdapter_PageSortING") OrderRepoAdapter orderRepoAdapter) {
        this.deliveryRepository = deliveryRepository;
        this.mapper = mapper;
        this.orderRepoAdapter = orderRepoAdapter;
    }

    @Override
    public IDelivery saveDelivery(IDelivery delivery) {
        DeliveryEntity deliveryEntity = deliveryRepository.save(mapper.getDeliveryEntityFromIDelivery(delivery));
        return mapper.getIDeliveryFromDeliveryEntity(deliveryEntity, delivery.getOrder());
    }

    @Override
    public IDelivery updateDelivery(IDelivery delivery) {
        deliveryRepository.save(mapper.getDeliveryEntityFromIDelivery(delivery));
        return delivery;
    }

    @Override
    public IDelivery getDeliveryById(Long id) {
        DeliveryEntity deliveryEntity = deliveryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery not found with id: " + id));
        return mapper.getIDeliveryFromDeliveryEntity(
                deliveryEntity,
                orderRepoAdapter.getOrderById(deliveryEntity.getOrderId()));
    }


    Page<IDelivery> getAllDeliveries(Pageable pageable) {
        return deliveryRepository.findAll(pageable).map(deliveryEntity -> {
            return getDeliveryById(deliveryEntity.getId());
        });
    }

    List<IDelivery> getAllDeliveries(Sort sort) {
        return deliveryRepository.findAll(sort).stream()
                .map(deliveryEntity -> {
                    return getDeliveryById(deliveryEntity.getId());
                })
                .toList();
    }

    Page<IDelivery> getDeliveriesByAddressContaining(String address, Pageable pageable){
        return deliveryRepository.findByAddressContaining(address, pageable)
                .map(deliveryEntity -> {return getDeliveryById(deliveryEntity.getId());});
    }

    Page<IDelivery> getDeliveriesByPhoneContaining(String phone, Pageable pageable){
        return deliveryRepository.findByPhoneContaining(phone, pageable)
                .map(deliveryEntity -> {return getDeliveryById(deliveryEntity.getId());});
    }

    List<IDelivery> getDeliveriesByDateTimeBetweenOrderByDateTimeDesc(
            LocalDateTime start,LocalDateTime end, Sort sort){
        return deliveryRepository.findByDateTimeBetweenOrderByDateTimeDesc(start, end, sort).stream()
                .map(deliveryEntity -> {return getDeliveryById(deliveryEntity.getId());}).toList();
    }

    Page<IDelivery> getDeliveriesByOrder(IOrder order, Pageable pageable){
        return deliveryRepository.findByOrderId(order.getId(), pageable)
                .map(deliveryEntity -> {return getDeliveryById(deliveryEntity.getId());});
    }

}
