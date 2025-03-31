package com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.entity;

import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Node("MenuItem")
public class MenuItemEntity  {
    @Id
    private UUID id = UUID.randomUUID();
    private String name;
    private MenuSection menuSection;
    private BigDecimal price;
}
