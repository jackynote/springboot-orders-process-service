package cafe.shop.repository;

import cafe.shop.model.entities.Order;
import cafe.shop.model.entities.Queue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    Page<Order> findByCustomerId(UUID customerId, Pageable pageable);

    Page<Order> findAll(Specification<Order> spec, Pageable pageable);

    List<Order> findByQueue(Queue queue);

}
