package com.aslanjavasky.shawarmadelviry.presentation.service;

import com.aslanjavasky.shawarmadelviry.domain.interractor.UserInterractor;
import com.aslanjavasky.shawarmadelviry.domain.repo.UserRepo;

public class UserService  extends UserInterractor {

    public UserService(UserRepo userRepo) {
        super(userRepo);
    }
}
