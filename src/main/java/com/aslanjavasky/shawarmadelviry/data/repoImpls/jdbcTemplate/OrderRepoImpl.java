package com.aslanjavasky.shawarmadelviry.data.repoImpls.jdbcTemplate;

import com.aslanjavasky.shawarmadelviry.domain.model.*;
import com.aslanjavasky.shawarmadelviry.domain.repo.MenuItemRepo;
import com.aslanjavasky.shawarmadelviry.domain.repo.OrderRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

@Slf4j
@Repository("ORwJT")
public class OrderRepoImpl implements OrderRepo {

    private final JdbcTemplate jdbcTemplate;


    public OrderRepoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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

        String sql = """
                SELECT
                O.id AS order_id,
                U.id AS user_id,
                U.name AS user_name,
                U.email,
                U.password,
                U.telegram,
                U.phone,
                U.address,
                O.date_time,
                O.status,
                O.total_price
                FROM orders O
                JOIN users U ON O.user_id=U.id
                WHERE O.user_id=?
                ORDER BY O.id
                """;

        return jdbcTemplate.query(sql, new RowMapper<IOrder>() {
            @Override
            public IOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
                IOrder order = createOrderFromRS(rs);
                order.setUser(createUserFromRS(rs));
                return order;
            }
        }, user.getId());
    }


    @Override
    public List<IOrder> getOrdersByStatus(OrderStatus orderStatus) {
        String sql = """
                SELECT 
                    U.id AS user_id,
                    U.name AS user_name,
                    U.email,
                    U.password,
                    U.telegram,
                    U.phone, 
                    U.address,
                    OMI.menu_item_id,
                    MI.name as menu_item_name,
                    MI.menu_section,
                    MI.price,
                    OMI.order_id,
                    O.date_time,
                    O.status,
                    O.total_price
                FROM orders O
                JOIN users U ON O.user_id=U.id
                JOIN orders_menu_items OMI ON O.id=OMI.order_id
                JOIN menu_items MI ON MI.id=OMI.menu_item_id
                WHERE O.status=?
                ORDER BY O.id
                """;
        return jdbcTemplate.query(sql,
                (rs) -> {
                    Map<Long, IOrder> orderMap = new LinkedHashMap<>();
                    while (rs.next()) {
                        Long orderId = rs.getLong("order_id");
                        IOrder order = orderMap.computeIfAbsent(orderId, id -> {
                            try {
                                IOrder newOrder = createOrderFromRS(rs);
                                newOrder.setUser(createUserFromRS(rs));
                                newOrder.setItemList(new ArrayList<IMenuItem>());
                                return newOrder;
                            } catch (SQLException e) {
                                throw new RuntimeException("Failed to get order by status");
                            }
                        });
                        order.getItemList().add(createMenuItemFromRS(rs));
                    }
                    return new ArrayList<>(orderMap.values());
                },
                orderStatus.name());

    }


    public IOrder getOrderById(Long orderId) {

        if (orderId == null) throw new IllegalArgumentException("orderId cannot be null");

        String sql = """
                SELECT 
                    U.id AS user_id,
                    U.name AS user_name,
                    U.email,
                    U.password,
                    U.telegram,
                    U.phone, 
                    U.address,
                    OMI.menu_item_id,
                    MI.name as menu_item_name,
                    MI.menu_section,
                    MI.price,
                    OMI.order_id,
                    O.date_time,
                    O.status,
                    O.total_price
                FROM orders O
                JOIN users U ON O.user_id=U.id
                JOIN orders_menu_items OMI ON O.id=OMI.order_id
                JOIN menu_items MI ON MI.id=OMI.menu_item_id
                WHERE O.id=?
                ORDER BY O.id
                """;

        return jdbcTemplate.query(sql, (rs) -> {
            IOrder order=null;
            while (rs.next()) {
                if (order == null){
                    order = createOrderFromRS(rs);
                    order.setUser(createUserFromRS(rs));
                    order.setItemList(new ArrayList<>());
                }
                order.getItemList().add(createMenuItemFromRS(rs));
            }
            return order;
        }, orderId);
    }

    private MenuItem createMenuItemFromRS(ResultSet rs) throws SQLException {
        MenuItem menuItem = new MenuItem();
        menuItem.setId(rs.getLong("menu_item_id"));
        menuItem.setName(rs.getString("menu_item_name"));
        menuItem.setMenuSection(MenuSection.valueOf(rs.getString("menu_section")));
        menuItem.setPrice(rs.getBigDecimal("price"));
        return menuItem;
    }

    private IOrder createOrderFromRS(ResultSet rs) throws SQLException {
        IOrder newOrder = new Order();
        newOrder.setId(rs.getLong("order_id"));
        newOrder.setDateTime(rs.getTimestamp("date_time").toLocalDateTime());
        newOrder.setStatus(OrderStatus.valueOf(rs.getString("status")));
        newOrder.setTotalPrice(rs.getBigDecimal("total_price"));
        return newOrder;
    }

    private IUser createUserFromRS(ResultSet rs) throws SQLException {
        IUser user = new User();
        user.setId(rs.getLong("user_id"));
        user.setName(rs.getString("user_name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setTelegram(rs.getString("telegram"));
        user.setPhone(rs.getString("phone"));
        user.setAddress(rs.getString("address"));
        return user;
    }
}
