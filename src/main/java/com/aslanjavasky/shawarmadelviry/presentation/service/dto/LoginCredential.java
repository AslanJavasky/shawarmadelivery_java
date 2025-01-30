package com.aslanjavasky.shawarmadelviry.presentation.service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginCredential {
    @NotBlank(message = "Email required")
    @Email(message = "Email should be valid")
    private String email;
    @NotBlank(message = "Password required")
    @Size(min = 6, message = "Password should be at least 6 characters")
    private String password;
}
