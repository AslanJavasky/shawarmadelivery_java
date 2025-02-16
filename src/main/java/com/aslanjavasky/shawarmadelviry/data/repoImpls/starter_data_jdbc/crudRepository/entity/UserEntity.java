package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.entity;

import com.aslanjavasky.shawarmadelviry.domain.model.IUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data @AllArgsConstructor @NoArgsConstructor
@Table("users")
public class UserEntity implements IUser {
    @Id
    private Long id;
    private String name;
    private String email;
    private String password;
    private String telegram;
    private String phone;
    private String address;
}
