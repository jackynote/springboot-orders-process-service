package cafe.shop.model.entities;

import cafe.shop.model.BaseTimestamp;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "menu_items")
public class MenuItem extends BaseTimestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Column(name = "is_available", nullable = false)
    private boolean isAvailable;

    @ManyToOne
    @JoinColumn(name = "franchise_id", nullable = false)
    private Franchise franchise;
}
