package com.aslanjavasky.shawarmadelviry.data.repoImpls.mongodb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "deliveries")
public class DeliveryEntity {

    @Id
    private UUID id = UUID.randomUUID();
    private String address;
    private String phone;
    private LocalDateTime dateTime;
    private UUID orderId;

}
