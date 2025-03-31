package com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.entity.OrderEntity;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.entity.UserEntity;
import com.aslanjavasky.shawarmadelviry.domain.model.OrderStatus;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderNeo4jRepository extends Neo4jRepository<OrderEntity, UUID> {
    List<OrderEntity> findByUser(UserEntity user);
    List<OrderEntity> findByStatus(OrderStatus orderStatus);
}
