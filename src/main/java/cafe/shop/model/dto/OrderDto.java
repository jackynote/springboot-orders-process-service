package cafe.shop.model.dto;

import cafe.shop.model.constant.OrderStatus;
import cafe.shop.model.entities.MenuItem;
import cafe.shop.model.entities.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private UUID orderId;
    private String orderNumber;
    private int queuePosition;
    private int expectedWaitingTime;
    private OrderStatus orderStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}