package com.aslanjavasky.shawarmadelviry.data.repoImpls.collectionFrw;

import com.aslanjavasky.shawarmadelviry.domain.model.User;
import com.aslanjavasky.shawarmadelviry.domain.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

@Repository("URwLL")
@Slf4j
public class UserRepoImplWithLinkedList implements UserRepo {

    private final List<User> users = new LinkedList<>();
//    private final Logger log = Logger.getLogger("UserRepoImplWithLinkedList");

    @Override
    public User saveUser(User user) {
        users.add(user);
        log.info("User created in LinkedList!");
        return user;
    }

    @Override
    public void deleteUser(User user) {
        log.info("User deleted from LinkedList!");
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
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).equals(user)) {
                users.set(i, user);
            }
        }
        return user;
    }
}
