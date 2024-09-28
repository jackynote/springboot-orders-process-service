package cafe.shop.repository;

import cafe.shop.model.entities.CustomerScore;
import cafe.shop.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerScoreRepository extends JpaRepository<CustomerScore, UUID> {

    Optional<CustomerScore> findByCustomer(User customer);


}
