package com.aslanjavasky.shawarmadelviry.data.repoImpls.jdbcTemplate;

import com.aslanjavasky.shawarmadelviry.domain.model.*;
import com.aslanjavasky.shawarmadelviry.domain.repo.MenuItemRepo;
import com.aslanjavasky.shawarmadelviry.domain.repo.OrderRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository("ORwJT")
public class OrderRepoImpl implements OrderRepo {

    private final JdbcTemplate jdbcTemplate;
    private final MenuItemRepo menuItemRepo;
    private final UserRepoImpl userRepoImpl;

    public OrderRepoImpl(JdbcTemplate jdbcTemplate, @Qualifier("MRwJT") MenuItemRepo menuItemRepo, @Qualifier("URwJT") UserRepoImpl userRepoImpl) {
        this.jdbcTemplate = jdbcTemplate;
        this.menuItemRepo = menuItemRepo;
        this.userRepoImpl = userRepoImpl;
    }

    @Override
    public IOrder saveOrder(IOrder order) {

        if (order == null) throw new IllegalArgumentException("order cannot be null");

        String sqlOrder = "INSERT INTO orders(date_time, status, user_id, total_price) VALUES(?,?,?,?);";
        String sqlOrderMenuitems = "INSERT INTO orders_menu_items(order_id, menu_item_id) VALUES(?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int affectedRow = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlOrder, new String[]{"id"});
            ps.setTimestamp(1, Timestamp.valueOf(order.getDateTime()));
            ps.setString(2, order.getStatus().name());
            ps.setLong(3, order.getUser().getId());
            ps.setBigDecimal(4, order.getTotalPrice());
            return ps;
        }, keyHolder);

        if (affectedRow == 0) throw new RuntimeException("Failed to save order, no rows affected");

        order.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());

        List<Object[]> batchArgs = new ArrayList<>();
        for (IMenuItem item : order.getItemList()) {
            batchArgs.add(new Object[]{order.getId(), item.getId()});
        }
        jdbcTemplate.batchUpdate(sqlOrderMenuitems, batchArgs);
        return order;
    }

    @Override
    public IOrder updateOrder(IOrder order) {

        if (order == null) throw new IllegalArgumentException("order cannot be null");

        String sql = "UPDATE orders SET date_time=?, status=?, user_id=?, total_price=?  WHERE id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setTimestamp(1, Timestamp.valueOf(order.getDateTime()));
            ps.setString(2, order.getStatus().name());
            ps.setLong(3, order.getUser().getId());
            ps.setBigDecimal(4, order.getTotalPrice());
            ps.setLong(5, order.getId());

            int affectedRow = ps.executeUpdate();
            if (affectedRow == 0) throw new SQLException("Failed to update order, no rows affected");

            return order;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public IOrder updateOrderStatus(Long orderId, OrderStatus status) {

        if (orderId == null) throw new IllegalArgumentException("orderId cannot be null");

        String sql = "UPDATE orders SET status=? WHERE id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, status.name());
            ps.setLong(2, orderId);

            int affectedRow = ps.executeUpdate();
            if (affectedRow == 0) throw new SQLException("Failed to update order status, no rows affected");

            return getOrderById(orderId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<IOrder> getOrdersByUser(IUser user) {

        if (user == null) throw new IllegalArgumentException("user cannot be null");

        List<IOrder> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setLong(1, user.getId());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Long orderId = rs.getLong("id");
                    IOrder order = getOrderById(orderId);
                    if (order != null) orders.add(order);
                }
            }

            return orders;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<IOrder> getOrdersByStatus(OrderStatus orderStatus) {
        List<IOrder> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE status=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, orderStatus.name());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Long orderId = rs.getLong("id");
                    IOrder order = getOrderById(orderId);
                    if (order != null) orders.add(order);
                }
            }

            return orders;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public IOrder getOrderById(Long orderId) {

        if (orderId == null) throw new IllegalArgumentException("orderId cannot be null");

        String sql = "SELECT * FROM orders WHERE id=?";
        String sqlFromOrdersMenuItems = "SELECT * FROM orders_menu_items WHERE order_id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             PreparedStatement psFromOrdersMenuItems = connection.prepareStatement(sqlFromOrdersMenuItems)
        ) {
            ps.setLong(1, orderId);
            Order order = new Order();
            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    order.setId(rs.getLong("id"));
                    order.setDateTime(rs.getTimestamp("date_time").toLocalDateTime());
                    order.setStatus(OrderStatus.valueOf(rs.getString("status")));
                    order.setUser(userRepoImpl.getUserById(rs.getLong("user_id")));
                    order.setTotalPrice(rs.getBigDecimal("total_price"));
                }
            }
            psFromOrdersMenuItems.setLong(1, orderId);
            List<IMenuItem> menuItems = new ArrayList<>();
            try (ResultSet rs = psFromOrdersMenuItems.executeQuery()) {
                while (rs.next()) {
                    IMenuItem menuItem = new MenuItem();
                    Long menuItemId = rs.getLong("menu_item_id");
                    menuItem = menuItemRepo.getMenuItemById(menuItemId);
                    if (menuItem != null) menuItems.add(menuItem);
                }
            }
            order.setItemList(menuItems);
            return order;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
