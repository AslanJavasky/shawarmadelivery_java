package com.aslanjavasky.shawarmadelviry.presentation.service;

import com.aslanjavasky.shawarmadelviry.domain.interractor.UserInterractor;
import com.aslanjavasky.shawarmadelviry.domain.repo.UserRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserService extends UserInterractor {

    private UserRepo userRepo;

    public UserService(@Qualifier("UserRepoAdapter_Cassandra") UserRepo userRepo) {
        super(userRepo);
    }



}
