package com.aslanjavasky.shawarmadelviry.data.repoImpls.jdbcTemplate;

import com.aslanjavasky.shawarmadelviry.domain.model.Delivery;
import com.aslanjavasky.shawarmadelviry.domain.model.IDelivery;
import com.aslanjavasky.shawarmadelviry.domain.repo.DeliveryRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Objects;

@Repository("DRwJT")
public class DeliveryRepoImpl implements DeliveryRepo {

    private final JdbcTemplate jdbcTemplate;
    private final OrderRepoImpl orderRepo;

    public DeliveryRepoImpl(JdbcTemplate jdbcTemplate, @Qualifier("ORwJT") OrderRepoImpl orderRepo) {
        this.jdbcTemplate = jdbcTemplate;
        this.orderRepo = orderRepo;
    }

    @Override
    public IDelivery saveDelivery(IDelivery delivery) {

        if (delivery == null) throw new IllegalArgumentException("Delivery cannot be null");

        String sql = "INSERT INTO deliveries(address, phone, date_time, order_id) VALUES(?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int affectedRow = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, delivery.getAddress());
            ps.setString(2, delivery.getPhone());
            ps.setTimestamp(3, Timestamp.valueOf(delivery.getDateTime()));
            ps.setLong(4, delivery.getOrder().getId());

            return ps;
        }, keyHolder);

        delivery.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return delivery;
    }

    @Override
    public IDelivery updateDelivery(IDelivery delivery) {

        if (delivery == null) throw new IllegalArgumentException("Delivery cannot be null");

        String sql = "UPDATE deliveries SET address= ? , phone=? , date_time= ?, order_id=? WHERE id=?";

        int affectedRow = jdbcTemplate.update(sql,
                delivery.getAddress(),
                delivery.getPhone(),
                Timestamp.valueOf(delivery.getDateTime()),
                delivery.getOrder().getId(),
                delivery.getId());
        if (affectedRow == 0) throw new RuntimeException("Failed to update delivery, no rows affected");

        return delivery;
    }

    @Override
    public IDelivery getDeliveryById(Long id) {

        if (id == null) throw new IllegalArgumentException("id cannot be null");

        String sql = "SELECT * FROM deliveries WHERE id=?";

        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, numRow) -> {
            IDelivery delivery = new Delivery();
            delivery.setId(rs.getLong("id"));
            delivery.setAddress(rs.getString("address"));
            delivery.setPhone(rs.getString("phone"));
            delivery.setDateTime(rs.getTimestamp("date_time").toLocalDateTime());
            delivery.setOrder(orderRepo.getOrderById(rs.getLong("order_id")));

            return delivery;
        });

    }
}
