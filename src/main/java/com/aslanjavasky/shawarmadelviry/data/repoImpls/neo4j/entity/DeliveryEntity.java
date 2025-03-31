package com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Node("Delivery")
public class DeliveryEntity {

    @Id
    private UUID id = UUID.randomUUID();
    private String address;
    private String phone;
    private LocalDateTime dateTime;
    @Relationship(type = "FOR_ORDER",direction = Relationship.Direction.OUTGOING)
    private OrderEntity order;

}
