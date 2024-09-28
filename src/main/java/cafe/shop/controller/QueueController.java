package cafe.shop.controller;

import cafe.shop.model.dto.*;
import cafe.shop.model.entities.Franchise;
import cafe.shop.service.CustomerService;
import cafe.shop.service.FranchiseService;
import cafe.shop.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/queues")
public class QueueController {

    @Autowired
    QueueService queueService;

    @Autowired
    CustomerService customerService;

    @Autowired
    FranchiseService franchiseService;

    @GetMapping
    public ResponseEntity<List<QueueDto>> getQueueByFranchise(@RequestParam(value = "franchiseId") UUID franchiseId) {
        Franchise franchise = franchiseService.getFranchise(franchiseId);
        List<QueueDto> queueList = queueService.getQueuesByFranchise(franchise);
        return ResponseEntity.ok(queueList);
    }


    @GetMapping("/{queueId}/status")
    public ResponseEntity<QueueStatusDto> getQueueStatus(@PathVariable UUID queueId) {
        QueueStatusDto status = queueService.getQueueStatus(queueId);
        return ResponseEntity.ok(status);
    }

    @GetMapping("/{queueId}/orders")
    public ResponseEntity<List<CustomerQueueDto>> getOrdersInQueue(@PathVariable UUID queueId) {
        List<CustomerQueueDto> ordersInQueue = queueService.getOrdersInQueue(queueId);
        return ResponseEntity.ok(ordersInQueue);
    }

    @PostMapping("/{queueId}/service")
    public ResponseEntity<String> serviceCustomer(@PathVariable UUID queueId, @RequestBody ServiceCustomerRequest request) {
        queueService.serviceCustomer(queueId, request.getCustomerQueueId());
        return ResponseEntity.ok("Customer has been serviced and removed from the queue.");
    }
}
