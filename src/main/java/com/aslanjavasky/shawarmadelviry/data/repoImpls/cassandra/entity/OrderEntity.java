package com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.entity;

import com.aslanjavasky.shawarmadelviry.domain.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table("orders")
public class OrderEntity  {

    @PrimaryKey
    private UUID id = UUID.randomUUID();

    @Column("date_time")
    private LocalDateTime dateTime;

    @Column
    private OrderStatus status;

    @Column("user_id")
    private UUID userId;

    @Column("total_price")
    private BigDecimal totalPrice;

    @Column("menu_items")
    private List<UUID> menuItemIds;

}
