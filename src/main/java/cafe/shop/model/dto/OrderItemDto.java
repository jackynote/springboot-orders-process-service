package cafe.shop.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemDto {
    private String name;
    private int quantity;
}