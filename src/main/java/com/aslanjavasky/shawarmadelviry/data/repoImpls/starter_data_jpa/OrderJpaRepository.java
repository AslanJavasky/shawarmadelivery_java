package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity.OrderEntity;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity.UserEntity;
import com.aslanjavasky.shawarmadelviry.domain.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findByUser(UserEntity user);

    List<OrderEntity> findByStatusOrderByDateTimeDesc(OrderStatus orderStatus);

}
