//package com.aslanjavasky.shawarmadelviry.data.repoImpls.redis.entity;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.redis.core.RedisHash;
//import org.springframework.data.redis.core.index.Indexed;
//
//import java.io.Serializable;
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@RedisHash("deliveries")
//public class DeliveryEntity implements Serializable {
//
//    @Id
//    private UUID id = UUID.randomUUID();
//    private String address;
//    private String phone;
//    private LocalDateTime dateTime;
//    @Indexed
//    private UUID orderId;
//
//}
