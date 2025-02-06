package com.aslanjavasky.shawarmadelviry.data.repoImpls.jdbcTemplate;

import com.aslanjavasky.shawarmadelviry.domain.model.*;
import com.aslanjavasky.shawarmadelviry.domain.repo.MenuItemRepo;
import com.aslanjavasky.shawarmadelviry.domain.repo.OrderRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
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

        int affectedRow = jdbcTemplate.update(sql, Timestamp.valueOf(order.getDateTime()), order.getStatus().name(), order.getUser().getId(), order.getTotalPrice(), order.getId());

        if (affectedRow == 0) throw new RuntimeException("Failed to update order, no rows affected");
        return order;
    }

    @Override
    public IOrder updateOrderStatus(Long orderId, OrderStatus status) {

        if (orderId == null) throw new IllegalArgumentException("orderId cannot be null");

        String sql = "UPDATE orders SET status=? WHERE id=?";

        int affectedRow = jdbcTemplate.update(sql, status.name(), orderId);
        if (affectedRow == 0) throw new RuntimeException("Failed to update order status, no rows affected");
        return getOrderById(orderId);
    }


    @Override
    public List<IOrder> getOrdersByUser(IUser user) {

        if (user == null) throw new IllegalArgumentException("user cannot be null");

        List<IOrder> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id=?";

        return jdbcTemplate.query(sql, new Object[]{user.getId()}, (rs, numRow) -> {
            Long orderId = rs.getLong("id");
            return getOrderById(orderId);
        });
    }


    @Override
    public List<IOrder> getOrdersByStatus(OrderStatus orderStatus) {
        String sql = "SELECT * FROM orders WHERE status=?";

        return jdbcTemplate.query(sql, new Object[]{orderStatus.name()}, (rs, numRow) -> {
            Long orderId = rs.getLong("id");
            return getOrderById(orderId);
        });
    }

    public IOrder getOrderById(Long orderId) {

        if (orderId == null) throw new IllegalArgumentException("orderId cannot be null");

        String sql = "SELECT * FROM orders WHERE id=?";

        return jdbcTemplate.queryForObject(sql, new Object[]{orderId}, (rs, numRow) -> {
            IOrder order = new Order();
            order.setId(rs.getLong("id"));
            order.setDateTime(rs.getTimestamp("date_time").toLocalDateTime());
            order.setStatus(OrderStatus.valueOf(rs.getString("status")));
            order.setUser(userRepoImpl.getUserById(rs.getLong("user_id")));
            order.setTotalPrice(rs.getBigDecimal("total_price"));
            order.setItemList(getMenuItemsForOrder(order.getId()));
            return order;
        });
    }

    private List<IMenuItem> getMenuItemsForOrder(Long orderId) {
        String sql = "SELECT * FROM orders_menu_items WHERE order_id=?";

        return jdbcTemplate.query(sql, new Object[]{orderId}, (rs, numRow) -> {
            Long menuItemId = rs.getLong("menu_item_id");
            return menuItemRepo.getMenuItemById(menuItemId);
        });
    }
}
