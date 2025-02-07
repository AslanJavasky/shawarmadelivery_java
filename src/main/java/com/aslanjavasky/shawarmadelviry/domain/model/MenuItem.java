package com.aslanjavasky.shawarmadelviry.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@AllArgsConstructor @NoArgsConstructor
public class MenuItem implements IMenuItem {
    private Long id;
    private String name;
    private MenuSection menuSection;
    private BigDecimal price;

}
