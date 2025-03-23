package com.aslanjavasky.shawarmadelviry.data.repoImpls.mongodb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class UserEntity {
    @Id
    private UUID id = UUID.randomUUID();
    private String name;
    private String email;
    private String password;
    private String telegram;
    private String phone;
    private String address;
}
