package com.aslanjavasky.shawarmadelviry.domain.interractor;

import com.aslanjavasky.shawarmadelviry.domain.model.IUser;
import com.aslanjavasky.shawarmadelviry.domain.repo.UserRepo;


public class UserInterractor {

    private final UserRepo repo;

    public UserInterractor(UserRepo userRepo) {
        this.repo = userRepo;
    }

    public IUser createUser(IUser user){
        return repo.saveUser(user);
    }

    public void deleteUser(IUser user){
        repo.deleteUser(user);
    }

    public void deleteUserByEmail(String email){
        repo.deleteUserByEmail(email);
    }

    public IUser getUserByEmail(String email){
        return repo.getUserByEmail(email);
    }

    public IUser updateUser(IUser user) {
        return repo.updateUser(user);
    }

}
