package com.aslanjavasky.shawarmadelviry.presentation.service;

import com.aslanjavasky.shawarmadelviry.conf.AuthUtils;
import com.aslanjavasky.shawarmadelviry.domain.interractor.UserInterractor;
import com.aslanjavasky.shawarmadelviry.domain.model.IUser;
import com.aslanjavasky.shawarmadelviry.domain.model.User;
import com.aslanjavasky.shawarmadelviry.domain.repo.UserRepo;
import com.aslanjavasky.shawarmadelviry.presentation.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService extends UserInterractor {

    private UserRepo userRepo;
    private final AuthUtils authUtils;

    public UserService(@Qualifier("UserRepoAdapter_JPA") UserRepo userRepo, AuthUtils authUtils) {
        super(userRepo);
        this.authUtils = authUtils;
    }

    public IUser registerUser(UserDto userDto){
        String encodedPassword=authUtils.encodePassword(userDto.getPassword());
        userDto.setPassword(encodedPassword);
        userDto.setUsername(encodedPassword);
        return userRepo.saveUser(userDto);
    }
}
