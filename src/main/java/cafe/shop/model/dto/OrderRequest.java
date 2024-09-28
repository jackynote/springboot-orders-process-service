package cafe.shop.model.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class OrderRequest {

    private UUID franchiseId;
    private List<OrderItemRequest> items;
}