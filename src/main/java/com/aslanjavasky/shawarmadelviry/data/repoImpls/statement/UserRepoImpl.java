package com.aslanjavasky.shawarmadelviry.data.repoImpls.statement;

import com.aslanjavasky.shawarmadelviry.domain.model.IUser;
import com.aslanjavasky.shawarmadelviry.domain.model.User;
import com.aslanjavasky.shawarmadelviry.domain.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Slf4j
@Repository("URwPS")
public class UserRepoImpl implements UserRepo {

    private final DataSource dataSource;

    public UserRepoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public IUser saveUser(IUser user) {
        if (user == null) throw new IllegalArgumentException("User cannot be null");

        String sql = "INSERT INTO users ( name, email, password, telegram, phone, address) VALUES(?,?,?,?,?,?);";

        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getTelegram());
            ps.setString(5, user.getPhone());
            ps.setString(6, user.getAddress());

            int affectedRow = ps.executeUpdate();
            if (affectedRow == 0) throw new SQLException("Failed to save user, no rows affected");

            try (ResultSet rs = ps.getGeneratedKeys()) {
                while (rs.next()) {
                    user.setId(rs.getLong("id"));
                }
            }

            connection.commit();
            return user;
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return null;
        } finally {
            try {
                if (connection != null) connection.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteUser(IUser user) {
        if (user == null) throw new IllegalArgumentException("User cannot be null");
        String sql = "DELETE FROM users WHERE id = ?";

        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setLong(1, user.getId());
                int affectedRow = ps.executeUpdate();
                if (affectedRow == 0) throw new SQLException("Failed to delete user, no rows affected");
                connection.commit();
            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                e.printStackTrace();
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void deleteUserByEmail(String email) {
        if (email == null) throw new IllegalArgumentException("Email cannot be null");
        String sql = "DELETE FROM users WHERE email = ? ";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            int affectedRow = ps.executeUpdate();
            if (affectedRow == 0) throw new SQLException("Failed to delete user, no rows affected");
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
            return null;
        }
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
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getTelegram());
            ps.setString(5, user.getPhone());
            ps.setString(6, user.getAddress());
            ps.setLong(7, user.getId());

            int affectedRow = ps.executeUpdate();
            if (affectedRow == 0) throw new SQLException("Failed to update user, no rows affected");

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
