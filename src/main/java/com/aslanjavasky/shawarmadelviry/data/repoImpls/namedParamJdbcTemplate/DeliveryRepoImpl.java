package com.aslanjavasky.shawarmadelviry.data.repoImpls.namedParamJdbcTemplate;

import com.aslanjavasky.shawarmadelviry.domain.model.*;
import com.aslanjavasky.shawarmadelviry.domain.repo.DeliveryRepo;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Repository("DRwNPJT")
public class DeliveryRepoImpl implements DeliveryRepo {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DeliveryRepoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Transactional
    @Override
    public IDelivery saveDelivery(IDelivery delivery) {

        if (delivery == null) throw new IllegalArgumentException("Delivery cannot be null");

        String sql = "INSERT INTO deliveries(address, phone, date_time, order_id) " +
                "VALUES(:address, :phone, :date_time, :order_id)";

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("address", delivery.getAddress())
                .addValue("phone", delivery.getPhone())
                .addValue("date_time", delivery.getDateTime())
                .addValue("order_id", delivery.getOrder().getId());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int affectedRow =
                namedParameterJdbcTemplate.update(sql, params, keyHolder, new String[]{"id"});


        delivery.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return delivery;
    }

    @Transactional
    @Override
    public IDelivery updateDelivery(IDelivery delivery) {

        if (delivery == null) throw new IllegalArgumentException("Delivery cannot be null");

        String sql = "UPDATE deliveries SET address = :address , phone = :phone , " +
                "date_time = :date_time, order_id = :order_id WHERE id = :id";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("address", delivery.getAddress());
        paramMap.put("phone", delivery.getPhone());
        paramMap.put("date_time", delivery.getDateTime());
        paramMap.put("order_id", delivery.getOrder().getId());
        paramMap.put("id", delivery.getId());

        SqlParameterSource params=new MapSqlParameterSource()
                .addValues(paramMap);

        int affectedRow = namedParameterJdbcTemplate.update(sql, params);
        if (affectedRow == 0) throw new RuntimeException("Failed to update delivery, no rows affected");

        return delivery;
    }

    @Transactional
    @Override
    public IDelivery getDeliveryById(Long id) {

        if (id == null) throw new IllegalArgumentException("id cannot be null");

        String sql = """
                SELECT 
                	D.id AS delivery_id,
                	D.date_time AS delivery_date_time,
                	D.order_id,
                	O.date_time AS order_date_time,
                	O.status,
                	O.user_id,
                	O.total_price,
                	U.name AS user_name,
                	U.email,
                	U.password,
                	U.telegram,
                	U.phone,
                	U.address,
                	OMI.menu_item_id,
                	MI.name AS menu_item_name,
                	MI.menu_section,
                	MI.price
                FROM deliveries D 
                JOIN orders O ON D.order_id=O.id
                JOIN users U ON U.id=O.user_id 
                JOIN orders_menu_items OMI ON O.id=OMI.order_id
                JOIN menu_items	MI ON OMI.menu_item_id=MI.id
                WHERE D.id = :id
                ORDER BY D.id
                """;
        return namedParameterJdbcTemplate.query(
                sql,
                new MapSqlParameterSource("id",id),
                new ResultSetExtractor<IDelivery>() {
            @Override
            public IDelivery extractData(ResultSet rs) throws SQLException, DataAccessException {
                IDelivery delivery = null;
                while (rs.next()) {
                    if (delivery == null) {
                        delivery = createDeliveryFromRS(rs);
                        delivery.setOrder(createOrderFromRS(rs));
                        delivery.getOrder().setUser(createUserFromRS(rs));
                        delivery.getOrder().setItemList(new ArrayList<>());
                    }
                    delivery.getOrder().getItemList().add(createMenuItemFromRS(rs));
                }
                if (delivery == null) {
                    throw new RuntimeException("No delivery found with id:" + id);
                }
                return delivery;

            }
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
        newOrder.setDateTime(rs.getTimestamp("order_date_time").toLocalDateTime());
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

    private IDelivery createDeliveryFromRS(ResultSet rs) throws SQLException {
        IDelivery delivery = new Delivery();
        delivery.setId(rs.getLong("delivery_id"));
        delivery.setAddress(rs.getString("address"));
        delivery.setPhone(rs.getString("phone"));
        delivery.setDateTime(rs.getTimestamp("delivery_date_time").toLocalDateTime());
        return delivery;
    }
}
