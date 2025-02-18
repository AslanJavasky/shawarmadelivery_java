package com.aslanjavasky.shawarmadelviry.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @AllArgsConstructor @NoArgsConstructor
public class Delivery implements IDelivery {
    private Long id;
    private String address;
    private String phone;
    private LocalDateTime dateTime;
    private IOrder order;
}
