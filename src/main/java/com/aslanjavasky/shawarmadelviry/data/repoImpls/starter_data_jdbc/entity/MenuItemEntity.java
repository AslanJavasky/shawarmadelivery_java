package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity;

import com.aslanjavasky.shawarmadelviry.domain.model.IMenuItem;
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;


@Data @NoArgsConstructor @AllArgsConstructor
@Table("menu_items")
public class MenuItemEntity implements IMenuItem {
    @Id
    private Long id;
    private String name;
    private MenuSection menuSection;
    private BigDecimal price;
}
