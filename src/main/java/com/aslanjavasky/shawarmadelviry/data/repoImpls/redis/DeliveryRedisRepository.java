package com.aslanjavasky.shawarmadelviry.data.repoImpls.redis;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.redis.entity.DeliveryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DeliveryRedisRepository extends CrudRepository<DeliveryEntity, UUID> { }
