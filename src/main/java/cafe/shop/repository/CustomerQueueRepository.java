package cafe.shop.repository;

import cafe.shop.model.entities.CustomerQueue;
import cafe.shop.model.entities.Order;
import cafe.shop.model.entities.Queue;
import cafe.shop.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerQueueRepository extends JpaRepository<CustomerQueue, UUID> {

    List<CustomerQueue> findAllByQueueOrderByCreatedAtAsc(Queue queue);

    Optional<CustomerQueue> findByOrder(Order order);
}
