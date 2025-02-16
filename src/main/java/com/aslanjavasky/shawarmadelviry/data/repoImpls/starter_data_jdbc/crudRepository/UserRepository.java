package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.entity.UserEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("UserRepoExtCrudRepo")
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    @Query("DELETE FROM users WHERE email = :email")
    void deleteByEmail(String email);
    UserEntity getUserByEmail(String email);
}
