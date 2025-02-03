package com.aslanjavasky.shawarmadelviry.data.repoImpls.statement;

import com.aslanjavasky.shawarmadelviry.domain.model.IUser;
import com.aslanjavasky.shawarmadelviry.domain.model.User;
import com.aslanjavasky.shawarmadelviry.domain.repo.UserRepo;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.*;

@Repository("URwPS")
public class UserRepoImpl implements UserRepo {

    private final DataSource dataSource;

    public UserRepoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public IUser saveUser(IUser user) {
        String sql="INSERT INTO users(name, email,password,telegram,phone,address) VALUES(?,?,?,?,?,?);";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1,user.getName());
            ps.setString(2,user.getEmail());
            ps.setString(3,user.getPassword());
            ps.setString(4,user.getTelegram());
            ps.setString(5,user.getPhone());
            ps.setString(6,user.getAddress());
            ps.executeUpdate();
            return user;
        } catch (SQLException e) {
            return null;
        }

    }

    @Override
    public void deleteUser(IUser user) {
        String sql="DELETE FROM users WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, user.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteUserByEmail(String email) {
        String sql = "DELETE FROM users WHERE email = ? ";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1,email);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public IUser getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email= ? ";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, email);
            User user = new User();
            try(ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    user.setId(rs.getLong("id"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setTelegram(rs.getString("telegram"));
                    user.setPhone(rs.getString("phone"));
                    user.setAddress(rs.getString("address"));
                }
            }
            return user;

        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public IUser updateUser(IUser user) {
        String sql = "UPDATE users SET " +
                "name= ?, " +
                "email= ?, "  +
                "password= ?,"  +
                "telegram= ?, " +
                "phone= ? ," +
                "address= ? " +
                "WHERE id= ? " ;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql )) {
            ps.setString(1,user.getName());
            ps.setString(2,user.getEmail());
            ps.setString(3,user.getPassword());
            ps.setString(4,user.getTelegram());
            ps.setString(5,user.getPhone());
            ps.setString(6,user.getAddress());
            ps.setLong(7,user.getId());
            ps.executeUpdate();
            return user;
        } catch (SQLException e) {
            return null;
        }
    }

    public IUser getUserById(long id) {
        String sql = "SELECT * FROM users WHERE id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setLong(1, id);
            User user = new User();
            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    user.setId(rs.getLong("id"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setTelegram(rs.getString("telegram"));
                    user.setPhone(rs.getString("phone"));
                    user.setAddress(rs.getString("address"));
                }
            }
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
