package com.aslanjavasky.shawarmadelviry.domain.model;

import lombok.Data;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Data
public class User {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String telegram;
    private String address;

//    @PostConstruct
//    public void init() {
//        System.out.println("init method is invoked!");
//        this.setTelegram("@Aslan_Javasky");
//    }
//
//    @PreDestroy
//    public void destroy() {
//        System.out.println("pre destroy method is invoked!");
//    }


}
