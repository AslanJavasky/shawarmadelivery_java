package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity;

import com.aslanjavasky.shawarmadelviry.domain.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data @NoArgsConstructor @AllArgsConstructor
@Table("orders")
public class OrderEntity {
    @Id
    private Long id;
    private LocalDateTime dateTime;
    private OrderStatus status;
    private Long userId;
    private BigDecimal totalPrice;
}
