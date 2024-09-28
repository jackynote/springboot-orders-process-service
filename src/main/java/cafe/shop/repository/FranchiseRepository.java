package cafe.shop.repository;

import cafe.shop.model.entities.Franchise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface FranchiseRepository extends JpaRepository<Franchise, UUID> {

    @Query("SELECT f FROM Franchise f LEFT JOIN FETCH f.queues WHERE f.id = :id")
    Optional<Franchise> findByIdWithQueues(@Param("id") UUID id);
}
