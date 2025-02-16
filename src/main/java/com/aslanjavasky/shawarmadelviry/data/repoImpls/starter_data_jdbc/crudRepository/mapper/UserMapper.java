package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.mapper;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.entity.UserEntity;
import com.aslanjavasky.shawarmadelviry.domain.model.IUser;
import com.aslanjavasky.shawarmadelviry.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity getUserEntityFromIUser(IUser iUser){
        return new UserEntity(
                iUser.getId(),
                iUser.getName(),
                iUser.getEmail(),
                iUser.getPassword(),
                iUser.getTelegram(),
                iUser.getPhone(),
                iUser.getAddress()
        );
    }

    public IUser getIUserFromUserEntity(UserEntity userEntity){
        return new User(
                userEntity.getId(),
                userEntity.getName(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getTelegram(),
                userEntity.getPhone(),
                userEntity.getAddress()
        );
    }

}
