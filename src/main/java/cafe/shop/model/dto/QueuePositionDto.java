package cafe.shop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QueuePositionDto {
    private int queuePosition;
    private int expectedWaitingTime;
}
