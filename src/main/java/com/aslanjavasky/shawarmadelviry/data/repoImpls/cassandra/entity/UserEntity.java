package com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.entity;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.UUIDUtils;
import com.aslanjavasky.shawarmadelviry.domain.model.IUser;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("users")
public class UserEntity {

    @PrimaryKey
    private UUID id = UUID.randomUUID();
    @Column
    private String name;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String telegram;
    @Column
    private String phone;
    @Column
    private String address;

    @Column("order_ids")
    private List<UUID> orders;

}
