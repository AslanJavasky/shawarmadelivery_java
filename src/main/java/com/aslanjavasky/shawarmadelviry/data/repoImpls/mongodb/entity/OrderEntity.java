package com.aslanjavasky.shawarmadelviry.data.repoImpls.mongodb.entity;

import com.aslanjavasky.shawarmadelviry.domain.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "orders")
public class OrderEntity {

    @Id
    private UUID id = UUID.randomUUID();
    private LocalDateTime dateTime;
    private OrderStatus status;
    private UUID userId;
    private BigDecimal totalPrice;
    private List<UUID> menuItemIds;

}
