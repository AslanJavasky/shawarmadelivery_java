package com.aslanjavasky.shawarmadelviry.domain.model;

import java.math.BigDecimal;

public interface IMenuItem {
    Long getId();

    void setId(Long id);

    String getName();

    void setName(String name);

    MenuSection getMenuSection();

    void setMenuSection(MenuSection menuSection) ;

    BigDecimal getPrice();

    void setPrice(BigDecimal price);
}
