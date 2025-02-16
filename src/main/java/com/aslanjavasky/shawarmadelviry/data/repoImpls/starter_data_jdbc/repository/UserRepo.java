package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.repository;

import com.aslanjavasky.shawarmadelviry.domain.model.User;
import org.springframework.data.repository.Repository;

public interface UserRepo extends Repository<User,Long> {

    //save(User user)
    //findById(Long userId)
    //existsById(Long userd)
    //findAll()
    //count()
    //deleteById(Long userId)
    //delete(User user)
    //deleteAll()

    void deleteUserByEmail(String email);
    User getUserByEmail(String email);

}
