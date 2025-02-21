package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.pagingAndSortingRepository;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.UserRepository;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPSRepository extends PagingAndSortingRepository<UserEntity, Long>,
        UserRepository {

    @Override
//    @Query("SELECT * FROM users ORDER BY id ASC LIMIT 10 OFFSET 0")
    Page<UserEntity> findAll(Pageable pageable);

    @Override
//    @Query("SELECT * FROM users ORDER BY name ASC")
    Iterable<UserEntity> findAll(Sort sort);

//    @Query("SELECT * FROM users WHERE email ILIKE '%email%' ORDER BY id ASC LIMIT 10 OFFSET 0")
    Page<UserEntity> findByEmailContaining(String email, Pageable pageable);

//    @Query("SELECT * FROM users ORDER BY name ASC")
    List<UserEntity> findAllByOrderByNameAsc();

//    @Query("SELECT * FROM users ORDER BY name DESC LIMIT 10 OFFSET 0")
    Page<UserEntity> findAllByOrderByNameDesc(Pageable pageable);



}
