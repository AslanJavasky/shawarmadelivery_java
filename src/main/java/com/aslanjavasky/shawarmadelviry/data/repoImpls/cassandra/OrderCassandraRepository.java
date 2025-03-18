package com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.entity.OrderEntity;
import com.aslanjavasky.shawarmadelviry.domain.model.OrderStatus;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrderCassandraRepository extends CassandraRepository<OrderEntity, UUID> {
    @Query("SELECT * FROM orders WHERE user_id = ?0 ALLOW FILTERING")
    List<OrderEntity> findByUser(UUID userId);
    @Query("SELECT * FROM orders WHERE status=?0 ALLOW FILTERING")
    List<OrderEntity> findByStatus(OrderStatus orderStatus);

}
