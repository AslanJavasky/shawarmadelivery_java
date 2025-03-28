package com.aslanjavasky.shawarmadelviry.data.repoImpls.redis.entity;

import com.aslanjavasky.shawarmadelviry.domain.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@RedisHash("orders")
public class OrderEntity implements Serializable {

    @Id
    private UUID id = UUID.randomUUID();
    private LocalDateTime dateTime;
    @Indexed
    private OrderStatus status;
    @Indexed
    private UUID userId;
    private BigDecimal totalPrice;
    private List<UUID> menuItemIds;

}
