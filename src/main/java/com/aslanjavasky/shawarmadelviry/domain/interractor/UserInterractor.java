package com.aslanjavasky.shawarmadelviry.domain.interractor;

import com.aslanjavasky.shawarmadelviry.domain.model.User;
import com.aslanjavasky.shawarmadelviry.domain.repo.UserRepo;
import lombok.Setter;


public class UserInterractor {

    private final UserRepo repo;

    public UserInterractor(UserRepo userRepo) {
        this.repo = userRepo;
    }

    public User createUser(User user){
        return repo.saveUser(user);
    }

    public void deleteUser(User user){
        repo.deleteUser(user);
    }

    public User getUserByEmail(String email){
        return repo.getUserByEmail(email);
    }

    public User updateUser(User user) {
        return repo.updateUser(user);
    }

}
