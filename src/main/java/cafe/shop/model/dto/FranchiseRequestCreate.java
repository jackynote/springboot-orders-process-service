package cafe.shop.model.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class FranchiseRequestCreate {

    private String location;
    private String name;
    private String contactDetails;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private int numberOfQueues;
    private int maxQueueSize;
    private double latitude;
    private double longitude;
}
