package com.aslanjavasky.shawarmadelviry.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class User implements IUser {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String telegram;
    private String phone;
    private String address;
}
