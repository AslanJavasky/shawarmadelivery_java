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
//import java.util.UUID;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@RedisHash("users")
//public class UserEntity implements Serializable {
//    @Id
//    private UUID id = UUID.randomUUID();
//    private String name;
//    @Indexed
//    private String email;
//    private String password;
//    private String telegram;
//    private String phone;
//    private String address;
//}
