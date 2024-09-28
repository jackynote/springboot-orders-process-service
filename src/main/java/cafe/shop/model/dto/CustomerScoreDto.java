package cafe.shop.model.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CustomerScoreDto {
    private UUID customerId;
    private int score;

}