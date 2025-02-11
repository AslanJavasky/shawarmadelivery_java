package com.aslanjavasky.shawarmadelviry.data.repoImpls.jooq;

import com.aslanjavasky.shawarmadelviry.domain.model.IUser;
import com.aslanjavasky.shawarmadelviry.domain.model.User;
import com.aslanjavasky.shawarmadelviry.domain.repo.UserRepo;
import com.aslanjavasky.shawarmadelviry.generated.jooq.tables.records.UsersRecord;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import static com.aslanjavasky.shawarmadelviry.generated.jooq.Tables.USERS;

@Slf4j
@Repository("URwJOOQ")
public class UserRepoImpl implements UserRepo {

    private final DSLContext dslContext;

    public UserRepoImpl(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public IUser saveUser(IUser user) {
        if (user == null) throw new IllegalArgumentException("User cannot be null");

        UsersRecord record = dslContext.insertInto(USERS)
                .set(USERS.NAME, user.getName())
                .set(USERS.EMAIL, user.getEmail())
                .set(USERS.PASSWORD, user.getPassword())
                .set(USERS.TELEGRAM, user.getTelegram())
                .set(USERS.PHONE, user.getPhone())
                .set(USERS.ADDRESS, user.getAddress())
                .returning(USERS.ID)
                .fetchOne();
        if (record == null) throw new RuntimeException("Failed to save user");
        user.setId(Long.valueOf(record.getId()));
        return user;
    }


    @Override
    public void deleteUser(IUser user) {
        if (user == null) throw new IllegalArgumentException("User cannot be null");

        int affectedRow = dslContext.deleteFrom(USERS)
                .where(USERS.ID.eq(user.getId()))
                .execute();
        if (affectedRow == 0) throw new RuntimeException("Failed to delete user, no rows affected");

    }

    @Override
    public void deleteUserByEmail(String email) {
        if (email == null) throw new IllegalArgumentException("Email cannot be null");

        int affectedRow = dslContext.deleteFrom(USERS)
                .where(USERS.EMAIL.eq(email))
                .execute();
        if (affectedRow == 0) throw new RuntimeException("Failed to delete user, no rows affected");
    }


    @Override
    public IUser getUserByEmail(String email) {

        return dslContext.selectFrom(USERS)
                .where(USERS.EMAIL.eq(email))
                .fetchOneInto(User.class);
    }

    @Override
    public IUser updateUser(IUser user) {

        int affectedRow = dslContext.update(USERS)
                .set(USERS.NAME, user.getName())
                .set(USERS.EMAIL, user.getEmail())
                .set(USERS.PASSWORD, user.getPassword())
                .set(USERS.TELEGRAM, user.getTelegram())
                .set(USERS.PHONE, user.getPhone())
                .set(USERS.ADDRESS, user.getAddress())
                .where(USERS.ID.eq(user.getId()))
                .execute();
        if (affectedRow == 0) throw new RuntimeException("Failed to update user, no rows affected");
        return user;
    }

    public IUser getUserById(Long id) {

        return dslContext.selectFrom(USERS)
                .where(USERS.ID.eq(id))
                .fetchOneInto(User.class);
    }

}
