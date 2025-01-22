package com.aslanjavasky.shawarmadelviry.data.repoImpls.collectionFrw;

import com.aslanjavasky.shawarmadelviry.domain.model.User;
import com.aslanjavasky.shawarmadelviry.domain.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
//import java.util.logging.Logger;

@Repository("URwAL")
@Slf4j
public class UserRepoImpl implements UserRepo {

    private final List<User> users = new ArrayList<>();
//    private final Logger log = Logger.getLogger("UserRepoImpl");
//    private final Logger log= LoggerFactory.getLogger(UserRepoImpl.class);

    @Override
    public User saveUser(User user) {
        users.add(user);
        log.info("User created!");
        return user;
    }

    @Override
    public void deleteUser(User user) {
        log.info("User deleted!");
        users.remove(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return users.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public User updateUser(User user) {
        int index = users.indexOf(user);
        if (index != -1) users.set(index, user);
        return user;
    }
}
