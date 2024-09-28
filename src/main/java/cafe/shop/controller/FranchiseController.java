package cafe.shop.controller;

import cafe.shop.model.dto.ErrorResponse;
import cafe.shop.model.dto.FranchiseDto;
import cafe.shop.model.dto.FranchiseRequestCreate;
import cafe.shop.model.entities.Franchise;
import cafe.shop.service.FranchiseService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/franchises")
@Log4j2
public class FranchiseController {

    @Autowired
    private FranchiseService franchiseService;

    @PostMapping
    public ResponseEntity<Franchise> createFranchise(@RequestBody FranchiseRequestCreate franchiseDto) {
        Franchise franchise = franchiseService.createFranchise(franchiseDto);
        return ResponseEntity.ok(franchise);
    }

    @PutMapping("/{franchiseId}")
    public ResponseEntity<Franchise> updateFranchise(
            @PathVariable UUID franchiseId,
            @RequestBody FranchiseRequestCreate franchiseDto) {
        Franchise updatedFranchise = franchiseService.updateFranchise(franchiseId, franchiseDto);
        return ResponseEntity.ok(updatedFranchise);
    }

    @GetMapping("/{franchiseId}")
    public ResponseEntity<Franchise> getFranchise(@PathVariable UUID franchiseId) {
        Franchise franchise = franchiseService.getFranchise(franchiseId);
        return ResponseEntity.ok(franchise);
    }

    @DeleteMapping("/{franchiseId}")
    public ResponseEntity<Void> deleteFranchise(@PathVariable UUID franchiseId) {
        franchiseService.deleteFranchise(franchiseId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/nearby")
    public ResponseEntity<?> getNearbyFranchises(@RequestParam double latitude, @RequestParam double longitude) {
        try {
            List<FranchiseDto> franchises = franchiseService.findNearbyFranchises(latitude, longitude);
            return ResponseEntity.ok(franchises);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Server error", "An error occurred while retrieving franchises"));
        }
    }
}
