package com.aslanjavasky.shawarmadelviry.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data @AllArgsConstructor
public class MenuItem {
    private Long id;
    private String name;
    private MenuSection menuSection;
    private BigDecimal price;

}
