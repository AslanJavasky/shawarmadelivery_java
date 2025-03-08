package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity;

import com.aslanjavasky.shawarmadelviry.domain.model.IUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.JDBCType;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@EntityListeners(AuditingEntityListener.class)
@Entity(name = "users")
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorColumn(name = "user_type")
//@Table(name = "users", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class UserEntity extends BaseEntity implements IUser {
    //    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_gen")
//    @SequenceGenerator(name = "user_seq_gen", sequenceName = "user_seq", allocationSize = 1)
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    @Column(nullable = false)
//    @Basic(optional = false,fetch = FetchType.EAGER)
//    @JdbcType(value= JDBCType.LONGNVARCHAR)
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

//    @Formula("(SELECT COUNT(*) FROM users)")
//    transient private Long userCount;




}
