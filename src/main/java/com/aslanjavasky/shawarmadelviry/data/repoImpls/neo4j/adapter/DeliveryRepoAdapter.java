//package com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.adapter;
//
//import com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.UUIDUtils;
//import com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.DeliveryNeo4jRepository;
//import com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.OrderNeo4jRepository;
//import com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.entity.DeliveryEntity;
//import com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.entity.mapper.DeliveryMapper;
//import com.aslanjavasky.shawarmadelviry.domain.model.IDelivery;
//import com.aslanjavasky.shawarmadelviry.domain.repo.DeliveryRepo;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component("DeliveryRepoAdapter_Neo4j")
//public class DeliveryRepoAdapter implements DeliveryRepo {
//
//    private final DeliveryNeo4jRepository deliveryRepository;
//    private final OrderNeo4jRepository orderRepository;
//    private final DeliveryMapper mapper;
//
//
//    public DeliveryRepoAdapter(
//            DeliveryNeo4jRepository deliveryRepository, OrderNeo4jRepository orderRepository,
//            @Qualifier("DeliveryM_Neo4j") DeliveryMapper mapper) {
//        this.deliveryRepository = deliveryRepository;
//        this.orderRepository = orderRepository;
//        this.mapper = mapper;
//    }
//
//    @Override
//    public IDelivery saveDelivery(IDelivery delivery) {
//
//        return mapper.getIDeliveryFromDeliveryEntity(deliveryRepository.save(
//                mapper.getDeliveryEntityFromIDelivery(delivery)));
//
//    }
//
//
//    @Override
//    public IDelivery updateDelivery(IDelivery delivery) {
//        return saveDelivery(delivery);
//    }
//
//    @Override
//    public IDelivery getDeliveryById(Long id) {
//        DeliveryEntity deliveryEntity = deliveryRepository.findById(UUIDUtils.getUUIDFromLong(id))
//                .orElseThrow(() -> new RuntimeException("Delivery not found with id: " + id));
//        return mapper.getIDeliveryFromDeliveryEntity(deliveryEntity);
//    }
//}
