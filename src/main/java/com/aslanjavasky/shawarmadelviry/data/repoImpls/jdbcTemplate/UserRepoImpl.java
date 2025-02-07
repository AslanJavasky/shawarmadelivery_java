package com.aslanjavasky.shawarmadelviry.data.repoImpls.jdbcTemplate;

import com.aslanjavasky.shawarmadelviry.domain.model.IUser;
import com.aslanjavasky.shawarmadelviry.domain.model.User;
import com.aslanjavasky.shawarmadelviry.domain.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.sql.*;
import java.util.Objects;

@Slf4j
@Repository("URwJT")
public class UserRepoImpl implements UserRepo {

    private final JdbcTemplate jdbcTemplate;

    public UserRepoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public IUser saveUser(IUser user) {
        if (user == null) throw new IllegalArgumentException("User cannot be null");

        String sql =
                "INSERT INTO users ( name, email, password, telegram, phone, address) VALUES(?,?,?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getTelegram());
            ps.setString(5, user.getPhone());
            ps.setString(6, user.getAddress());

            return ps;
        }, keyHolder);

        user.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return user;
    }


    @Override
    public void deleteUser(IUser user) {
        if (user == null) throw new IllegalArgumentException("User cannot be null");
        String sql = "DELETE FROM users WHERE id = ?";

        int affectedRow = jdbcTemplate.update(sql, user.getId());
        if (affectedRow == 0) throw new RuntimeException("Failed to delete user, no rows affected");

    }

    @Override
    public void deleteUserByEmail(String email) {
        if (email == null) throw new IllegalArgumentException("Email cannot be null");
        String sql = "DELETE FROM users WHERE email = ? ";
        int affectedRow = jdbcTemplate.update(sql, email);
        if (affectedRow == 0) throw new RuntimeException("Failed to delete user, no rows affected");
    }


    @Override
    public IUser getUserByEmail(String email) {
//        1)queryForObject
//        String sql = "SELECT * FROM users WHERE email= ? ";
//        return jdbcTemplate.queryForObject(sql, new Object[]{email}, (rs, rowNum) -> {
//            User user = new User();
//            user.setId(rs.getLong("id"));
//            user.setName(rs.getString("name"));
//            user.setEmail(rs.getString("email"));
//            user.setPassword(rs.getString("password"));
//            user.setTelegram(rs.getString("telegram"));
//            user.setPhone(rs.getString("phone"));
//            user.setAddress(rs.getString("address"));
//            return user;
//        });

//        2)query(sql, ParamArgs,ResultSetExtractor )
//        String sql = "SELECT * FROM users WHERE email= ? ";
//        return jdbcTemplate.query(sql, new Object[]{email}, rs -> {
//            while (rs.next()) {
//                User user = new User();
//                user.setId(rs.getLong("id"));
//                user.setName(rs.getString("name"));
//                user.setEmail(rs.getString("email"));
//                user.setPassword(rs.getString("password"));
//                user.setTelegram(rs.getString("telegram"));
//                user.setPhone(rs.getString("phone"));
//                user.setAddress(rs.getString("address"));
//                return user;
//            }
//            return null;
//        });

//        3)query(sql, RowMapper)
//        String sql = "SELECT * FROM users WHERE email=? ";
//        return jdbcTemplate.query(sql, (rs, numRow) -> {
//            if (rs.next()) {
//                User user = new User();
//                user.setId(rs.getLong("id"));
//                user.setName(rs.getString("name"));
//                user.setEmail(rs.getString("email"));
//                user.setPassword(rs.getString("password"));
//                user.setTelegram(rs.getString("telegram"));
//                user.setPhone(rs.getString("phone"));
//                user.setAddress(rs.getString("address"));
//                return user;
//            }
//            return null;
//        }).stream().findFirst().orElse(null);

//        4) query(sql, BeanPropertyRowMapper)
//        String sql = "SELECT * FROM users WHERE email= ? ";
//        return jdbcTemplate.query(
//                sql,
//                new Object[]{email},
//                new BeanPropertyRowMapper<>(User.class))
//                .stream().findFirst().orElse(null);

        // 5) queryForStream(sql, BeanPropertyRowMapper)
        String sql = "SELECT * FROM users WHERE email= ? ";
        return jdbcTemplate.queryForStream(
                        sql,
                        new BeanPropertyRowMapper<>(User.class),
                        new Object[]{email})
                .findFirst()
                .orElse(null);
    }

    @Override
    public IUser updateUser(IUser user) {
        String sql = "UPDATE users SET " +
                "name= ?, " +
                "email= ?, " +
                "password= ?," +
                "telegram= ?, " +
                "phone= ? ," +
                "address= ? " +
                "WHERE id= ? ";
        int affectedRow = jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getPassword(), user.getTelegram(),
                user.getPhone(), user.getAddress(), user.getId());
        if (affectedRow == 0) throw new RuntimeException("Failed to update user, no rows affected");
        return user;
    }

    public IUser getUserById(long id) {
        String sql = "SELECT * FROM users WHERE id=?";
        return jdbcTemplate
                .queryForStream(
                        sql,new BeanPropertyRowMapper<>(User.class),new Object[]{id})
                .findFirst()
                .orElse(null);



//        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
//            User user = new User();
//            user.setId(rs.getLong("id"));
//            user.setName(rs.getString("name"));
//            user.setEmail(rs.getString("email"));
//            user.setPassword(rs.getString("password"));
//            user.setTelegram(rs.getString("telegram"));
//            user.setPhone(rs.getString("phone"));
//            user.setAddress(rs.getString("address"));
//
//            return user;
//        });
    }

}
