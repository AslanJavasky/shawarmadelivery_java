package com.aslanjavasky.shawarmadelviry.domain.model;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.time.LocalDateTime;

@Data
public class Order {
    private Long id;
    private LocalDateTime dateTime;
    private OrderStatus status;
    private IUser user;
    private List<MenuItem> itemList;
    private BigDecimal totalPrice;
}
