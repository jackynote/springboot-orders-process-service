package cafe.shop.model.entities;

import cafe.shop.model.BaseTimestamp;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "customer_queue")
public class CustomerQueue extends BaseTimestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "queue_id", nullable = false)
    private Queue queue;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order; // Reference to the order if any

    @Column(name = "waiting_since", nullable = false)
    private LocalDateTime waitingSince; // Timestamp when the customer entered the queue
}
