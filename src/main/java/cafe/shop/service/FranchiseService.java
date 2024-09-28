package cafe.shop.service;

import cafe.shop.model.dto.FranchiseDto;
import cafe.shop.model.dto.FranchiseRequestCreate;
import cafe.shop.model.entities.Franchise;

import java.util.List;
import java.util.UUID;

public interface FranchiseService {

    Franchise createFranchise(FranchiseRequestCreate franchiseRequestCreate);

    Franchise updateFranchise(UUID franchiseId, FranchiseRequestCreate franchiseRequest);

    Franchise getFranchise(UUID franchiseId);

    void deleteFranchise(UUID franchiseId);

    List<FranchiseDto> findNearbyFranchises(double latitude, double longitude);

}
