package com.aslanjavasky.shawarmadelviry.data.repoImpls.redis.entity;

import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@RedisHash("menu_items")
public class MenuItemEntity implements Serializable {
    @Id
    private UUID id = UUID.randomUUID();
    private String name;
    @Indexed
    private MenuSection menuSection;
    private BigDecimal price;
}
