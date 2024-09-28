package cafe.shop.model.dto;

import cafe.shop.model.BaseTimestamp;
import cafe.shop.model.entities.CustomerQueue;
import cafe.shop.model.entities.Franchise;
import cafe.shop.model.entities.Order;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class QueueDto {

    private UUID id;
    private int queueNumber;
}
