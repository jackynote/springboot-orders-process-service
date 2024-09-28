package cafe.shop.service.impl;

import cafe.shop.exception.FranchiseNotFoundException;
import cafe.shop.model.AuthUser;
import cafe.shop.model.dto.FranchiseDto;
import cafe.shop.model.dto.FranchiseRequestCreate;
import cafe.shop.model.entities.Franchise;
import cafe.shop.model.entities.Queue;
import cafe.shop.repository.FranchiseRepository;
import cafe.shop.repository.MerchantRepository;
import cafe.shop.service.BaseService;
import cafe.shop.service.FranchiseService;
import cafe.shop.service.QueueService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
public class FranchiseServiceImpl extends BaseService implements FranchiseService {

    @Autowired
    private FranchiseRepository franchiseRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private QueueService queueService;

    @Override
    @Transactional
    public Franchise createFranchise(FranchiseRequestCreate franchiseRequestCreate) {
        AuthUser authUser = currentUser();

        Franchise franchise = new Franchise();
        franchise.setLocation(franchiseRequestCreate.getLocation());
        franchise.setName(franchiseRequestCreate.getName());
        franchise.setContactDetails(franchiseRequestCreate.getContactDetails());
        franchise.setOpeningTime(franchiseRequestCreate.getOpeningTime());
        franchise.setClosingTime(franchiseRequestCreate.getClosingTime());
        franchise.setNumberOfQueues(franchiseRequestCreate.getNumberOfQueues());
        franchise.setMaxQueueSize(franchiseRequestCreate.getMaxQueueSize());
        franchise.setLatitude(franchiseRequestCreate.getLatitude());
        franchise.setLongitude(franchiseRequestCreate.getLongitude());

        merchantRepository.findById(authUser.getMerchantId()).ifPresent(franchise::setMerchant);
        franchise = franchiseRepository.save(franchise);

        queueService.createQueueByFranchise(franchise);
        log.debug("Franchise with id: {} created successfully", franchise.getId().toString());

        return franchise;


    }

    @Override
    public Franchise updateFranchise(UUID franchiseId, FranchiseRequestCreate franchiseRequest) {
        Optional<Franchise> optionalFranchise = franchiseRepository.findById(franchiseId);

        if (optionalFranchise.isPresent()) {
            Franchise franchise = optionalFranchise.get();
            franchise.setLocation(franchiseRequest.getLocation());
            franchise.setName(franchiseRequest.getName());
            franchise.setContactDetails(franchiseRequest.getContactDetails());
            franchise.setOpeningTime(franchiseRequest.getOpeningTime());
            franchise.setClosingTime(franchiseRequest.getClosingTime());
            franchise.setNumberOfQueues(franchiseRequest.getNumberOfQueues());
            franchise.setMaxQueueSize(franchiseRequest.getMaxQueueSize());
            franchise.setLatitude(franchiseRequest.getLatitude());
            franchise.setLongitude(franchiseRequest.getLongitude());

            franchise = franchiseRepository.save(franchise);
            log.debug("Franchise with id: {} updated successfully", franchise.getId().toString());
            return franchise;

        } else {
            throw new FranchiseNotFoundException("Franchise not found with id: " + franchiseId);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Franchise getFranchise(UUID franchiseId) {
        Franchise franchise = franchiseRepository.findById(franchiseId)
                .orElseThrow(() -> new FranchiseNotFoundException("Franchise not found with id: " + franchiseId));

//        List<Queue> queueList = queueService.getQueuesByFranchise(franchise);
//        franchise.setQueues(new HashSet<>(queueList));
        return franchise;
    }

    @Override
    public void deleteFranchise(UUID franchiseId) {
        Franchise franchise = franchiseRepository.findById(franchiseId)
                .orElseThrow(() -> new FranchiseNotFoundException("Franchise not found with id: " + franchiseId));

        franchiseRepository.delete(franchise);
        log.debug("Franchise with id: {} deleted successfully", franchiseId);

    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the earth in km

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // Distance in km
    }

    public List<FranchiseDto> findNearbyFranchises(double latitude, double longitude) {
        List<Franchise> franchises = franchiseRepository.findAll();

        return franchises.stream()
                .map(franchise -> {
                    double distance = calculateDistance(latitude, longitude, franchise.getLatitude(), franchise.getLongitude());
                    return FranchiseDto.builder()
                            .id(franchise.getId())
                            .name(franchise.getName())
                            .location(franchise.getLocation())
                            .distance(distance)
                            .build();
                })
                .sorted(Comparator.comparingDouble(FranchiseDto::getDistance))
                .filter(item -> item.getDistance() <= 200) // filter franchises with max 200km
                .collect(Collectors.toList());
    }
}
