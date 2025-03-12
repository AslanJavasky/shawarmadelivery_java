package com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("deliveries")
public class DeliveryEntity  {

    @PrimaryKey
    private UUID id= UUID.randomUUID();

    @Column
    private String address;

    @Column
    private String phone;

    @Column("date_time")
    private LocalDateTime dateTime;

    @Column("order_id")
    private UUID orderId;

}
