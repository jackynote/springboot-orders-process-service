package cafe.shop.service;

import cafe.shop.model.dto.*;
import cafe.shop.model.entities.Franchise;
import cafe.shop.model.entities.Order;
import cafe.shop.model.entities.Queue;
import cafe.shop.model.entities.User;

import java.util.List;
import java.util.UUID;

public interface QueueService {

    void createQueueByFranchise(Franchise franchise);

    QueueStatusDto getQueueStatus(UUID queueId);

    List<CustomerQueueDto> getOrdersInQueue(UUID queueId);

    List<QueueDto> getQueuesByFranchise(Franchise franchise);

    void serviceCustomer(UUID queueId, UUID customerQueueId);

    Queue addOrderToQueue(Order order, User customer);

    int getCustomerQueueSize(Queue queue);

    void removeOrderFromQueue(Queue queue, Order order);
}
