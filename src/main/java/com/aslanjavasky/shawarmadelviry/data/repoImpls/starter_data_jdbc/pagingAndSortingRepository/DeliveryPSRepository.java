package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.pagingAndSortingRepository;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.DeliveryRepository;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.DeliveryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DeliveryPSRepository extends PagingAndSortingRepository<DeliveryEntity, Long>,
        DeliveryRepository {

    @Override
    Page<DeliveryEntity> findAll(Pageable pageable);

    @Override
    List<DeliveryEntity> findAll(Sort sort);

    Page<DeliveryEntity> findByAddressContaining(String address, Pageable pageable);

    Page<DeliveryEntity> findByPhoneContaining(String phone, Pageable pageable);

    List<DeliveryEntity> findByDateTimeBetweenOrderByDateTimeDesc(
            LocalDateTime startDate, LocalDateTime endDate, Sort sort);

    Page<DeliveryEntity> findByOrderId(Long orderId, Pageable pageable);
}
