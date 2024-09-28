package cafe.shop.model.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class QueueStatusDto {
    private UUID queueId;
    private int queueNumber;
    private int numberOfWaitingCustomers;
}