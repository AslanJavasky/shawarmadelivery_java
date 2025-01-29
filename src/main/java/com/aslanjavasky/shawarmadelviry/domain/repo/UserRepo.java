package com.aslanjavasky.shawarmadelviry.domain.repo;

import com.aslanjavasky.shawarmadelviry.domain.model.IUser;
import com.aslanjavasky.shawarmadelviry.domain.model.User;

public interface UserRepo {
    IUser saveUser(IUser user);
    void deleteUser(IUser user);
    IUser getUserByEmail(String email);
    IUser updateUser(IUser user);
}
