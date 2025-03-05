package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity;

import com.aslanjavasky.shawarmadelviry.domain.model.IMenuItem;
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection;
import com.aslanjavasky.shawarmadelviry.domain.model.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "menu_items")
public class MenuItemEntity implements IMenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "menu_section")
    private MenuSection menuSection;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

//    @ManyToMany(mappedBy = "itemList", cascade = CascadeType.ALL)
//    private List<OrderEntity> orders = new ArrayList<>();

}
