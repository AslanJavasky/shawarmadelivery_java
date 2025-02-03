package com.aslanjavasky.shawarmadelviry.domain.model;

import java.time.LocalDateTime;

public interface IDelivery {
    Long getId() ;

    void setId(Long id) ;

    String getAddress() ;

    void setAddress(String address) ;

    String getPhone() ;

    void setPhone(String phone) ;

    LocalDateTime getDateTime() ;

    void setDateTime(LocalDateTime dateTime) ;

    IOrder getOrder() ;

    void setOrder(IOrder order);
}
