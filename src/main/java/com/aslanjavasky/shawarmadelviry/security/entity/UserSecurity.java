package com.aslanjavasky.shawarmadelviry.security.entity;

import com.aslanjavasky.shawarmadelviry.domain.model.IUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "auth_users")
public class UserSecurity implements UserDetails, IUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name required")
    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String username;
    @NotBlank(message = "Email required")
    @Email(message = "Email should be valid")
    @Column(unique = true, nullable = false)
    private String email;
    @NotBlank(message = "Password required")
    @Size(min = 6, message = "Password should be at least 6 characters")
    @Column(nullable = false)
    private String password;

    private String telegram;
    @NotBlank(message = "Phone number required")
    @Pattern(regexp = "^\\+?\\d+$", message = "Only digits for phone number")
    private String phone;
    @NotBlank(message = "Address required")
    private String address;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(getRole().name()));
    }
}
