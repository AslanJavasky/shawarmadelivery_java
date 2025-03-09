package com.aslanjavasky.shawarmadelviry.data.repoImpls.namedParamJdbcTemplate;

import com.aslanjavasky.shawarmadelviry.domain.model.*;
import com.aslanjavasky.shawarmadelviry.domain.repo.OrderRepo;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

@Slf4j
@Repository("ORwNPJT")
public class OrderRepoImpl implements OrderRepo {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public OrderRepoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Transactional
    @Override
    public IOrder saveOrder(IOrder order) {

        if (order == null) throw new IllegalArgumentException("order cannot be null");

        String sqlOrder = "INSERT INTO orders(date_time, status, user_id, total_price) " +
                "VALUES( :date_time, :status , :user_id , :total_price);";

        SqlParameterSource paramsOrder = new MapSqlParameterSource()
                .addValue("date_time", Timestamp.valueOf(order.getDateTime()))
                .addValue("status", order.getStatus().name())
                .addValue("user_id", order.getUser().getId())
                .addValue("total_price", order.getTotalPrice());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int affectedRow = namedParameterJdbcTemplate.update(
                sqlOrder, paramsOrder, keyHolder, new String[]{"id"});
        if (affectedRow == 0) throw new RuntimeException("Failed to save order, no rows affected");

        order.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());

        String sqlOrderMenuitems = "INSERT INTO orders_menu_items( order_id , menu_item_id) " +
                "VALUES( :order_id , :menu_item_id );";

        SqlParameterSource[] batchArgs = new SqlParameterSource[order.getItemList().size()];
        int index = 0;
        for (IMenuItem item : order.getItemList()) {
            batchArgs[index++] = new MapSqlParameterSource()
                    .addValue("order_id", order.getId())
                    .addValue("menu_item_id", item.getId());
        }
        namedParameterJdbcTemplate.batchUpdate(
                sqlOrderMenuitems, batchArgs);
        return order;
    }

    @Transactional
    @Override
    public IOrder updateOrder(IOrder order) {

        if (order == null) throw new IllegalArgumentException("order cannot be null");

        String sql = "UPDATE orders SET date_time = :date_time, status = :status, " +
                "user_id = :user_id, total_price = :total_price WHERE id = :id";
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("date_time", Timestamp.valueOf(order.getDateTime()))
                .addValue("status", order.getStatus().name())
                .addValue("user_id", order.getUser().getId())
                .addValue("total_price", order.getTotalPrice())
                .addValue("id", order.getId());

        int affectedRow = namedParameterJdbcTemplate.update(sql, params);

        if (affectedRow == 0) throw new RuntimeException("Failed to update order, no rows affected");
        return order;
    }

    @Transactional
    @Override
    public IOrder updateOrderStatus(Long orderId, OrderStatus status) {

        if (orderId == null) throw new IllegalArgumentException("orderId cannot be null");

        String sql = "UPDATE orders SET status = :status WHERE id = :id";

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("status", status.name())
                .addValue("id", orderId);
        int affectedRow = namedParameterJdbcTemplate.update(sql, params);
        if (affectedRow == 0) throw new RuntimeException("Failed to update order status, no rows affected");
        return getOrderById(orderId);
    }


    @Transactional
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
                  O.total_price,
                  MI.id AS menu_item_id,
                  MI.name AS menu_item_name,
                  MI.menu_section,
                  MI.price
                FROM orders O
                JOIN users U ON O.user_id=U.id
                JOIN orders_menu_items OMI ON OMI.order_id=O.id
                JOIN menu_items MI ON MI.id=OMI.menu_item_id
                WHERE O.user_id = :user_id
                ORDER BY O.id
                """;

        return namedParameterJdbcTemplate.query(
                sql,
                new MapSqlParameterSource("user_id", user.getId()),
                new ResultSetExtractor<List<IOrder>>() {
                    @Override
                    public List<IOrder> extractData(ResultSet rs) throws SQLException, DataAccessException {
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
                    }
                });

    }


    @Transactional
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
                WHERE O.status= :order_status
                ORDER BY O.id
                """;

        return namedParameterJdbcTemplate.query(
                sql,
                new MapSqlParameterSource("order_status", orderStatus.name()),
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
                });

    }


    @Transactional
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
                WHERE O.id = :order_id
                ORDER BY O.id
                """;

        return namedParameterJdbcTemplate.query(
                sql,
                new MapSqlParameterSource("order_id", orderId),
                (rs) -> {
                    IOrder order = null;
                    while (rs.next()) {
                        if (order == null) {
                            order = createOrderFromRS(rs);
                            order.setUser(createUserFromRS(rs));
                            order.setItemList(new ArrayList<>());
                        }
                        order.getItemList().add(createMenuItemFromRS(rs));
                    }
                    return order;
                });
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
