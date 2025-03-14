package com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.entity;

import com.aslanjavasky.shawarmadelviry.domain.model.IMenuItem;
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table("menu_items")
public class MenuItemEntity implements IMenuItem {

    @PrimaryKey
    private UUID id = UUID.randomUUID();

    @Column
    private String name;

    @Column("menu_section")
    private MenuSection menuSection;

    @Column
    private BigDecimal price;

    @Override
    public Long getId() {
        return this.id.getMostSignificantBits();
    }

    @Override
    public void setId(Long id) {

        Long mostSignBit = id;
        Long leastSignBit = (id << 32) | (id >>> 32);

        this.id = id == null ? null : new UUID(mostSignBit, leastSignBit);
    }


}
