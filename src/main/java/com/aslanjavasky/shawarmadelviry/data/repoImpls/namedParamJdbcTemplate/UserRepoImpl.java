package com.aslanjavasky.shawarmadelviry.data.repoImpls.namedParamJdbcTemplate;

import com.aslanjavasky.shawarmadelviry.domain.model.IUser;
import com.aslanjavasky.shawarmadelviry.domain.model.User;
import com.aslanjavasky.shawarmadelviry.domain.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Repository("URwNPJT")
public class UserRepoImpl implements UserRepo {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public UserRepoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public IUser saveUser(IUser user) {
        if (user == null) throw new IllegalArgumentException("User cannot be null");

        String sql =
                "INSERT INTO users ( name, email, password, telegram, phone, address) " +
                        "VALUES(:name,:email,:password,:telegram,:phone, :address);";
//1)case HashMap -> new MapSqlParameterSource(map)
//        Map<String, Object> paramMap = new HashMap<>();
//        paramMap.put("name", user.getName());
//        paramMap.put("email", user.getEmail());
//        paramMap.put("password", user.getPassword());
//        paramMap.put("telegram", user.getTelegram());
//        paramMap.put("phone", user.getPhone());
//        paramMap.put("address", user.getAddress());
//        MapSqlParameterSource params=new MapSqlParameterSource(paramMap);

//        2)new MapSqlParameterSource().addValue()
//        SqlParameterSource params = new MapSqlParameterSource()
//                .addValue("name", user.getName())
//                .addValue("email", user.getEmail())
//                .addValue("password", user.getPassword())
//                .addValue("telegram", user.getTelegram())
//                .addValue("phone", user.getPhone())
//                .addValue("address", user.getAddress());

//        3)BeanPropertySqlParameterSource
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(user);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int affectedRow = namedParameterJdbcTemplate.update(
                sql, params, keyHolder, new String[]{"id"});
        if (affectedRow == 0) throw new RuntimeException("Failed to save user, no rows affected");
        user.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return user;
    }


    @Override
    public void deleteUser(IUser user) {
        if (user == null) throw new IllegalArgumentException("User cannot be null");
        String sql = "DELETE FROM users WHERE id = :id";

        int affectedRow = namedParameterJdbcTemplate.update(
                sql, new MapSqlParameterSource("id", user.getId()));
        if (affectedRow == 0) throw new RuntimeException("Failed to delete user, no rows affected");

    }

    @Override
    public void deleteUserByEmail(String email) {
        if (email == null) throw new IllegalArgumentException("Email cannot be null");
        String sql = "DELETE FROM users WHERE email = :email ";
        int affectedRow = namedParameterJdbcTemplate.update(
                sql, new MapSqlParameterSource("email", email));
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
        String sql = "SELECT * FROM users WHERE email= :email ";
        return namedParameterJdbcTemplate.queryForStream(
                        sql,
                        new MapSqlParameterSource("email", email),
                        new BeanPropertyRowMapper<>(User.class))
                .findFirst()
                .orElse(null);
    }

    @Override
    public IUser updateUser(IUser user) {
        String sql = "UPDATE users SET " +
                "name= :name, " +
                "email= :email, " +
                "password= :password," +
                "telegram= :telegram, " +
                "phone= :phone ," +
                "address= :address " +
                "WHERE id= :id ";
        int affectedRow = namedParameterJdbcTemplate.update(
                sql, new BeanPropertySqlParameterSource(user));
        if (affectedRow == 0) throw new RuntimeException("Failed to update user, no rows affected");
        return user;
    }

    public IUser getUserById(Long id) {
        String sql = "SELECT * FROM users WHERE id=:id";
        return namedParameterJdbcTemplate
                .queryForStream(
                        sql,
                        new MapSqlParameterSource("id", id)
                        , new BeanPropertyRowMapper<>(User.class))
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
