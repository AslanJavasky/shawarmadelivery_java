package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

//1:1 @OneToOne @JoinColumn
//1:N @OneToMany @JoinColumn
//N:1 @ManyToOne @JoinColumn
//N:M @ManyToMany @JoinTable

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "deliveries")
public class DeliveryEntity extends BaseEntity {

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phone;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;


}
