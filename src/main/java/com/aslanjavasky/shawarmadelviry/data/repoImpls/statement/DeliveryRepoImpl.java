package com.aslanjavasky.shawarmadelviry.data.repoImpls.statement;

import com.aslanjavasky.shawarmadelviry.domain.model.Delivery;
import com.aslanjavasky.shawarmadelviry.domain.model.IDelivery;
import com.aslanjavasky.shawarmadelviry.domain.repo.DeliveryRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Repository("DRwPS")
public class DeliveryRepoImpl implements DeliveryRepo {

    private final DataSource dataSource;
    private final OrderRepoImpl orderRepo;

    public DeliveryRepoImpl(DataSource dataSource,@Qualifier("ORwPS") OrderRepoImpl orderRepo) {
        this.dataSource = dataSource;
        this.orderRepo = orderRepo;
    }

    @Override
    public IDelivery saveDelivery(IDelivery delivery) {
        String sql = "INSERT INTO deliveries(address, phone, date_time, order_id) VALUES(?,?,?,?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setString(1, delivery.getAddress());
            ps.setString(2, delivery.getPhone());
            ps.setTimestamp(3, Timestamp.valueOf(delivery.getDateTime()));
            ps.setLong(4, delivery.getOrder().getId());

            ps.executeUpdate();

            return delivery;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public IDelivery updateDelivery(IDelivery delivery) {
        String sql = "UPDATE deliveries SET address= ? , phone=? , date_time= ?, order_id=? WHERE id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setString(1, delivery.getAddress());
            ps.setString(2, delivery.getPhone());
            ps.setTimestamp(3, Timestamp.valueOf(delivery.getDateTime()));
            ps.setLong(4, delivery.getOrder().getId());
            ps.setLong(5, delivery.getId());

            ps.executeUpdate();

            return delivery;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public IDelivery getDeliveryById(Long id) {
        String sql = "SELECT * FROM deliveries WHERE id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setLong(1,id);
            IDelivery delivery=new Delivery();
            try (ResultSet rs=ps.executeQuery()){
                while(rs.next()){

                    delivery.setId(rs.getLong("id"));
                    delivery.setAddress(rs.getString("address"));
                    delivery.setPhone(rs.getString("phone"));
                    delivery.setDateTime(rs.getTimestamp("date_time").toLocalDateTime());
                    delivery.setOrder(orderRepo.getOrderById(rs.getLong("order_id")));
                }
            }
            ps.executeUpdate();

            return delivery;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
