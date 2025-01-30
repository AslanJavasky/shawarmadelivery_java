package com.aslanjavasky.shawarmadelviry.presentation.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class OrderDto {
    @NotBlank(message = "Name required")
    private String username;
    @NotBlank(message = "Address required")
    private String address;
    @NotBlank(message = "Phone number required")
    @Pattern(regexp = "^\\+?\\d+$", message = "Only digits for phone number")
    private String phone;
}
