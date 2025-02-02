package com.aslanjavasky.shawarmadelviry.data.repoImpls.statement;

import com.aslanjavasky.shawarmadelviry.domain.model.IUser;
import com.aslanjavasky.shawarmadelviry.domain.model.User;
import com.aslanjavasky.shawarmadelviry.domain.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Repository("URwS")
public class UserRepoImpl implements UserRepo {

    private final DataSource dataSource;

    public UserRepoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public IUser saveUser(IUser user) {

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            String sql = "INSERT INTO users(name, email,password,telegram,phone,address) VALUES(" +
                    "'" + user.getName() + "','" + user.getEmail() + "','" + user.getPassword() + "','" +
                    user.getTelegram() + "','" + user.getPhone() + "','" + user.getAddress() + "'" +
                    ");";
            statement.executeUpdate(sql);
            return user;
        } catch (SQLException e) {
            return null;
        }

    }

    @Override
    public void deleteUser(IUser user) {

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            String sql = "DELETE FROM users WHERE id=" + user.getId();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public IUser getUserByEmail(String email) {

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            String sql = "SELECT * FROM users WHERE email='" + email + "';";
            ResultSet rs = statement.executeQuery(sql);
            User user = new User();
            while(rs.next()){
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setTelegram(rs.getString("telegram"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));

            }
            return user;

        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public IUser updateUser(IUser user) {

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            String sql = "UPDATE users SET " +
                    "name='" + user.getName() + "', " +
                    "email='" + user.getEmail() + "', " +
                    "password='" + user.getPassword() + "', " +
                    "telegram='" + user.getTelegram() + "', " +
                    "phone='" + user.getPhone() + "', " +
                    "address='" + user.getAddress() + "' " +
                    "WHERE id=" + user.getId() + ";";
            statement.executeUpdate(sql);
            return user;
        } catch (SQLException e) {
            return null;
        }
    }

}
