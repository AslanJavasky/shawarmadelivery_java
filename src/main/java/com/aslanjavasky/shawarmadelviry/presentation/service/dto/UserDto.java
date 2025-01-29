package com.aslanjavasky.shawarmadelviry.presentation.service.dto;

import com.aslanjavasky.shawarmadelviry.domain.model.IUser;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class UserDto implements IUser {
    private Long id;
    @NotBlank(message = "Name required")
    private String name;
    @NotBlank(message = "Email required")
    @Email(message = "Email should be valid")
//    @Pattern(regexp = "^[a-zA-Z0-9-]+@[a-zA-Z0-9]+\\.(a-zA-Z){2,}$")
    private String email;
    @NotBlank(message = "Password required")
    @Size(min = 6, message = "Password should be at least 6 characters")
    private String password;
    private String telegram;
    @NotBlank(message = "Phone number required")
//    @Digits(integer=11,fraction = 0)
    @Pattern(regexp = "^\\+?\\d+$", message = "Only digits for phone number")
    private String phone;
    @NotBlank(message = "Address required")
//    @Min(value = 4, message = "Address must be more than 4 characters")
    private String address;
}
