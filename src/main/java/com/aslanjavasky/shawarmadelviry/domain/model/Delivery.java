package com.aslanjavasky.shawarmadelviry.domain.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Delivery implements IDelivery {
    private Long id;
    private String address;
    private String phone;
    private LocalDateTime dateTime;
    private IOrder order;
}
