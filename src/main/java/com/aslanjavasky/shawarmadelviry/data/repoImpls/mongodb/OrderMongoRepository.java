//package com.aslanjavasky.shawarmadelviry.data.repoImpls.mongodb;
//
//import com.aslanjavasky.shawarmadelviry.data.repoImpls.mongodb.entity.OrderEntity;
//import com.aslanjavasky.shawarmadelviry.domain.model.OrderStatus;
//import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.stereotype.Repository;
//import java.util.List;
//import java.util.UUID;
//
//@Repository
//public interface OrderMongoRepository extends MongoRepository<OrderEntity, UUID> {
//    List<OrderEntity> findByUserId(UUID userId);
//    List<OrderEntity> findByStatus(OrderStatus orderStatus);
//}
