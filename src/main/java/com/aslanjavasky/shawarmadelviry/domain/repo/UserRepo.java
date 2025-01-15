package com.aslanjavasky.shawarmadelviry.domain.repo;

import com.aslanjavasky.shawarmadelviry.domain.model.User;

public interface UserRepo {
    User saveUser(User user);
    void deleteUser(User user);
    User getUserByEmail(String email);
    User updateUser(User user);
}
