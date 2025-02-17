package com.aslanjavasky.shawarmadelviry.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor
public class Order implements IOrder {
    private Long id;
    private LocalDateTime dateTime;
    private OrderStatus status;
    private IUser user;
    private List<IMenuItem> itemList;
    private BigDecimal totalPrice;
}
