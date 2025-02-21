package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor
@Table("deliveries")
public class DeliveryEntity {
    @Id
    private Long id;
    private String address;
    private String phone;
    private LocalDateTime dateTime;
    private Long orderId;
}
