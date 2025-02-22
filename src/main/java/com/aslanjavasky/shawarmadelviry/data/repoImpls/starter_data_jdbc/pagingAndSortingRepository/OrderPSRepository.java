package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.pagingAndSortingRepository;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.OrderRepository;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.OrderEntity;
import com.aslanjavasky.shawarmadelviry.domain.model.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository("OrderRepoExtPSRepo")
public interface OrderPSRepository extends PagingAndSortingRepository<OrderEntity, Long>,
        OrderRepository {

    @Override
    List<OrderEntity> findAll(Sort sort);

    @Override
    Page<OrderEntity> findAll(Pageable pageable);

    Page<OrderEntity> findByUserId(Long userId, Pageable pageable);

    Page<OrderEntity> findByStatus(OrderStatus status, Pageable pageable);

    List<OrderEntity> findByStatusOrderByDateTimeDesc(OrderStatus status, Sort sort);

    Page<OrderEntity> findByUserIdAndStatus(Long userId, OrderStatus status, Pageable pageable);

    Page<OrderEntity> findByTotalPriceGreaterThanEqual(BigDecimal price, Pageable pageable);

    List<OrderEntity> findByTotalPriceLessThanEqualOrderByDateTimeAsc(BigDecimal price, Sort sort);

}
