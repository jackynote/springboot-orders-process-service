package cafe.shop.repository;

import cafe.shop.model.entities.Franchise;
import cafe.shop.model.entities.Queue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QueueRepository extends JpaRepository<Queue, UUID> {

    Optional<Queue> findByIdAndFranchiseId(UUID queueId, UUID franchiseId);

    List<Queue> findAllByFranchise(Franchise franchise);
}
