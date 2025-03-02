package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "deliveries")
public class DeliveryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phone;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

}
