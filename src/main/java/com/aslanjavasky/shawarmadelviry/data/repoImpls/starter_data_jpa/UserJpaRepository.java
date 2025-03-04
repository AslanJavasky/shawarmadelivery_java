package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

//    @Query("DELETE FROM users WHERE email=:email")
    void deleteByEmail(String email);

    UserEntity findByEmail(String email);
}
