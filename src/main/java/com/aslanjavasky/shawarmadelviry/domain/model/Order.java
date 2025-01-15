package com.aslanjavasky.shawarmadelviry.domain.model;

import lombok.Data;
import java.util.List;
import java.time.LocalDateTime;

@Data
public class Order {
    private Long id;
    private LocalDateTime dateTime;
    private OrderStatus status;
    private User user;
    private List<MenuItem> itemList;
    private Integer totalPrice;
}
