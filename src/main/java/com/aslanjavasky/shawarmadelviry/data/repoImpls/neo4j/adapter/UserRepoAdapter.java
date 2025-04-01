package com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.adapter;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.UUIDUtils;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.UserNeo4jRepository;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.entity.UserEntity;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.entity.mapper.UserMapper;
import com.aslanjavasky.shawarmadelviry.domain.model.IUser;
import com.aslanjavasky.shawarmadelviry.domain.repo.UserRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("UserRepoAdapter_Neo4j")
public class UserRepoAdapter implements UserRepo {

    private final UserNeo4jRepository userRepository;
    private final UserMapper mapper;

    public UserRepoAdapter(UserNeo4jRepository userRepo,
                           @Qualifier("UserM_Neo4j") UserMapper mapper) {
        this.userRepository = userRepo;
        this.mapper = mapper;
    }


    @Override
    public IUser saveUser(IUser user) {
        return mapper.getIUserFromUserEntity(userRepository.save(
                mapper.getUserEntityFromIUser(user)));
    }


    @Override
    public void deleteUser(IUser user) {
        userRepository.delete(mapper.getUserEntityFromIUser(user));
    }


    @Override
    public void deleteUserByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity != null) userRepository.delete(userEntity);
//        UserEntity user = userRepository.findByEmail(email);
//        if (user != null) {
//            userRepository.delete(user);
//        } else {
//            throw new IllegalArgumentException("User with email:" + email + " not found");
//        }
    }


    @Override
    public IUser getUserByEmail(String email) {
        return mapper.getIUserFromUserEntity(userRepository.findByEmail(email));
    }


    @Override
    public IUser updateUser(IUser user) {
        return mapper.getIUserFromUserEntity(userRepository.save(
                mapper.getUserEntityFromIUser(user)));
    }


    public IUser getUserById(Long id) {
        Optional<UserEntity> userOptional = userRepository.findById(UUIDUtils.getUUIDFromLong(id));
        return userOptional.map(mapper::getIUserFromUserEntity).orElse(null);
    }

}
