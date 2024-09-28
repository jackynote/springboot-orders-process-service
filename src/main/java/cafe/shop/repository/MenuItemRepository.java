package cafe.shop.repository;

import cafe.shop.model.entities.Franchise;
import cafe.shop.model.entities.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MenuItemRepository extends JpaRepository<MenuItem, UUID> {

    List<MenuItem> findByFranchiseId(UUID franchiseId);

}
