package com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.entity;

import com.aslanjavasky.shawarmadelviry.domain.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Node("Order")
public class OrderEntity {
    @Id
    private UUID id = UUID.randomUUID();
    private LocalDateTime dateTime;
    private OrderStatus status;
    @Relationship(type = "ORDERED_BY", direction = Relationship.Direction.INCOMING)
    private UserEntity user = new UserEntity();
    private BigDecimal totalPrice;
    @Relationship(type = "CONTAINS", direction = Relationship.Direction.OUTGOING)
    private List<MenuItemEntity> itemList;
//    @Relationship(type = "HAS_DELIVERY", direction = Relationship.Direction.OUTGOING)
//    private DeliveryEntity delivery;

}
