package cafe.shop.model.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderItemRequest {
    private UUID menuItemId;
    private int quantity;
}