package cafe.shop.model.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ServiceCustomerRequest {
    private UUID customerQueueId;
}