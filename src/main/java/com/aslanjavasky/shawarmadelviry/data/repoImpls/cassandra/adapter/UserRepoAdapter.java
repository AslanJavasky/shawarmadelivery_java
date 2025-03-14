package com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.adapter;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.UserCassandraRepository;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.entity.UserEntity;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.entity.mapper.UserMapper;
import com.aslanjavasky.shawarmadelviry.domain.model.IUser;
import com.aslanjavasky.shawarmadelviry.domain.repo.UserRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

@Component("URwCassandra")
public class UserRepoAdapter implements UserRepo {

    private final UserCassandraRepository userRepository;
    private final UserMapper userMapper;

    public UserRepoAdapter(UserCassandraRepository userRepository,
                           @Qualifier("UserM_Cassandra") UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public IUser saveUser(IUser user) {

        UserEntity userEntity = userMapper.getUserEntityFromIUser(user);
        if (userEntity.getId() == null) userEntity.setId(new Random().nextLong());
        return userMapper.getIUserFromUserEntity(userRepository.save(userEntity));

    }

    @Override
    public void deleteUser(IUser user) {
        userRepository.delete(userMapper.getUserEntityFromIUser(user));
    }

    @Override
    public void deleteUserByEmail(String email) {
        UserEntity userEntity=userRepository.findByEmail(email);
        if (userEntity != null) userRepository.delete(userEntity);
    }

    @Override
    public IUser getUserByEmail(String email) {
        return userMapper.getIUserFromUserEntity(userRepository.findByEmail(email));
    }

    @Override
    public IUser updateUser(IUser user) {
        return userMapper.getIUserFromUserEntity(
                userRepository.save(userMapper.getUserEntityFromIUser(user)));
    }

    public IUser getUserById(UUID uuid) {
        return userRepository.findById(uuid).orElse(null);
    }
}
