package com.aslanjavasky.shawarmadelviry.data.repoImpls.redis;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.redis.entity.OrderEntity;
import com.aslanjavasky.shawarmadelviry.domain.model.OrderStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRedisRepository extends CrudRepository<OrderEntity, UUID> {
    List<OrderEntity> findByUserId(UUID userId);
    List<OrderEntity> findByStatus(OrderStatus orderStatus);
}
