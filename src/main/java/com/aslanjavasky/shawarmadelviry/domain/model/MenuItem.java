package com.aslanjavasky.shawarmadelviry.domain.model;

import lombok.Data;

@Data
public class MenuItem {
    private Long id;
    private String name;
    private MenuSection menuSection;
    private Integer price;
}
