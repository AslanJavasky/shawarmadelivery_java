package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.adapter;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity.UserEntity;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity.mapper.UserMapper;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.UserJpaRepository;
import com.aslanjavasky.shawarmadelviry.domain.model.IUser;
import com.aslanjavasky.shawarmadelviry.domain.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component("URwJPA")
public class UserRepoAdapter implements UserRepo {

    private final UserJpaRepository userRepository;
    private final UserMapper mapper;

    public UserRepoAdapter(UserJpaRepository userRepo,@Qualifier("UserM_JPA") UserMapper mapper) {
        this.userRepository = userRepo;
        this.mapper = mapper;
    }

    @Transactional
    @Override
    public IUser saveUser(IUser user) {
        return userRepository.save(
                mapper.getUserEntityFromIUser(user));
    }

    @Transactional
    @Override
    public void deleteUser(IUser user) {
        userRepository.delete(mapper.getUserEntityFromIUser(user));
    }

    @Transactional
    @Override
    public void deleteUserByEmail(String email) {
        userRepository.deleteByEmail(email);
//        UserEntity user = userRepository.findByEmail(email);
//        if (user != null) {
//            userRepository.delete(user);
//        } else {
//            throw new IllegalArgumentException("User with email:" + email + " not found");
//        }
    }

    @Transactional
    @Override
    public IUser getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    @Override
    public IUser updateUser(IUser user) {
        return userRepository.save(
                mapper.getUserEntityFromIUser(user)
        );
    }

    @Transactional
    public IUser getUserById(Long id) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        return userOptional.map(mapper::getIUserFromUserEntity).orElse(null);
    }

}
