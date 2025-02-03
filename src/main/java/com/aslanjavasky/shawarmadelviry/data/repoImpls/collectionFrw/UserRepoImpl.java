package com.aslanjavasky.shawarmadelviry.data.repoImpls.collectionFrw;

import com.aslanjavasky.shawarmadelviry.domain.model.IUser;
import com.aslanjavasky.shawarmadelviry.domain.model.User;
import com.aslanjavasky.shawarmadelviry.domain.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
//import java.util.logging.Logger;
 //JPA-Hibernate ORM -> PreperedStatement JOOQ SQL-HQL NoSQL
//Create(INSERT) Read(SELECT) Update Delete
//JDBC


@Repository("URwAL")
@Slf4j
public class UserRepoImpl implements UserRepo {

    private final List<IUser> users = new ArrayList<>();
    private final AtomicLong nextId=new AtomicLong(1);

//    private final Logger log = Logger.getLogger("UserRepoImpl");
//    private final Logger log= LoggerFactory.getLogger(UserRepoImpl.class);

    @Override
    public IUser saveUser(IUser user) {
        user.setId(nextId.getAndIncrement());
        users.add(user);
        log.info("User created!");
        return user;
    }

    @Override
    public void deleteUser(IUser user) {
        log.info("User deleted!");
        users.remove(user);
    }

    @Override
    public void deleteUserByEmail(String email) {

    }

    @Override
    public IUser getUserByEmail(String email) {
        return users.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public IUser updateUser(IUser user) {
        int index = users.indexOf(user);
        if (index != -1) users.set(index, user);
        return user;
    }
}
