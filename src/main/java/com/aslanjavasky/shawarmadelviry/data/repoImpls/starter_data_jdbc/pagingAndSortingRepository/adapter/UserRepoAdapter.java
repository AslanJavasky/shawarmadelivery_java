package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.pagingAndSortingRepository.adapter;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.UserEntity;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.mapper.UserMapper;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.pagingAndSortingRepository.UserPSRepository;
import com.aslanjavasky.shawarmadelviry.domain.model.IUser;
import com.aslanjavasky.shawarmadelviry.domain.repo.UserRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import java.util.Optional;
import java.util.List;

@Component("UserRepoAdapter_PageSortING")
public class UserRepoAdapter implements UserRepo {

    private final UserPSRepository userRepository;
    private final UserMapper mapper;

    public UserRepoAdapter(UserPSRepository repo, UserMapper mapper) {
        this.userRepository = repo;
        this.mapper = mapper;
    }


    @Override
    public IUser saveUser(IUser user) {
        return userRepository.save(
                mapper.getUserEntityFromIUser(user));
    }

    @Override
    public void deleteUser(IUser user) {
        userRepository.delete(mapper.getUserEntityFromIUser(user));
    }

    @Override
    public void deleteUserByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email);
        if (user != null) {
            userRepository.delete(user);
        } else {
            throw new IllegalArgumentException("User with email:" + email + " not found");
        }
    }

    @Override
    public IUser getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public IUser updateUser(IUser user) {
        return userRepository.save(
                mapper.getUserEntityFromIUser(user)
        );
    }

    public IUser getUserById(Long id) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        return userOptional.map(mapper::getIUserFromUserEntity).orElse(null);
    }

//    Pageable pageable1 = PageRequest.of(0, 10);
    public Page<IUser> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(mapper::getIUserFromUserEntity);
    }

//    Sort sort=Sort.by(Sort.Direction.ASC,"name");
    public List<IUser> getAllUsers(Sort sort) {
        return ((List<UserEntity>) userRepository.findAll(sort)).stream()
                .map(mapper::getIUserFromUserEntity).toList();
    }

    public Page<IUser> findByEmailContaining(String email, Pageable pageable ){
        return  userRepository.findByEmailContaining(email, pageable)
                .map(mapper::getIUserFromUserEntity);
    }

    public List<IUser> findAllByOrderByNameAsc(){
        return userRepository.findAllByOrderByNameAsc().stream()
                .map(mapper::getIUserFromUserEntity).toList();
    }

    public Page<IUser> findAllByOrderByNameDesc(Pageable pageable){
        return userRepository.findAllByOrderByNameDesc(pageable)
                .map(mapper::getIUserFromUserEntity);
    }

}