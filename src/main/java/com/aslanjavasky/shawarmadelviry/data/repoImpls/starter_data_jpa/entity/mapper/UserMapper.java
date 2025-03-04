package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity.mapper;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity.UserEntity;
import com.aslanjavasky.shawarmadelviry.domain.model.IUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component("UserM_JPA")
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserEntity getUserEntityFromIUser(IUser iUser) {
        if (iUser == null) return null;
        return modelMapper.map(iUser, UserEntity.class);

//        return new UserEntity(
//                iUser.getId(),
//                iUser.getName(),
//                iUser.getEmail(),
//                iUser.getPassword(),
//                iUser.getTelegram(),
//                iUser.getPhone(),
//                iUser.getAddress()
//        );
    }

    public IUser getIUserFromUserEntity(UserEntity userEntity) {
        if (userEntity == null) return null;
        return modelMapper.map(userEntity, IUser.class);

//        return new User(
//                userEntity.getId(),
//                userEntity.getName(),
//                userEntity.getEmail(),
//                userEntity.getPassword(),
//                userEntity.getTelegram(),
//                userEntity.getPhone(),
//                userEntity.getAddress()
//        );
    }

}
