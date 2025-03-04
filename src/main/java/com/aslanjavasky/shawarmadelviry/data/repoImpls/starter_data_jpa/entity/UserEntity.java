package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity;

import com.aslanjavasky.shawarmadelviry.domain.model.IUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity(name = "users")
//@Table(name = "users")
public class UserEntity implements IUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    private String telegram;
    private String phone;
    private String address;

    @OneToMany(mappedBy = "user")
    private List<OrderEntity> orders;

}
