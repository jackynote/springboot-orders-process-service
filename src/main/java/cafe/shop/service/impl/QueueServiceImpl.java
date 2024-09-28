package cafe.shop.service.impl;

import cafe.shop.exception.ResourceNotFoundException;
import cafe.shop.model.constant.OrderStatus;
import cafe.shop.model.dto.*;
import cafe.shop.model.entities.*;
import cafe.shop.model.entities.Queue;
import cafe.shop.repository.*;
import cafe.shop.service.BaseService;
import cafe.shop.service.QueueService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Log4j2
public class QueueServiceImpl extends BaseService implements QueueService {

    @Autowired
    private QueueRepository queueRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerQueueRepository customerQueueRepository;

    @Autowired
    private CustomerScoreRepository customerScoreRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void createQueueByFranchise(Franchise franchise) {
        List<Queue> queueList = new ArrayList<>();
        for (int i = 0; i < franchise.getNumberOfQueues(); i++) {
            Queue queue = new Queue();
            queue.setFranchise(franchise);
            queue.setQueueNumber(i + 1);
            queueList.add(queue);
        }
        queueRepository.saveAll(queueList);
    }

    @Override
    public QueueStatusDto getQueueStatus(UUID queueId) {
        Queue queue = queueRepository.findById(queueId)
                .orElseThrow(() -> new ResourceNotFoundException("Queue not found"));

        List<CustomerQueue> waitingCustomers = customerQueueRepository.findAllByQueueOrderByCreatedAtAsc(queue);

        QueueStatusDto status = new QueueStatusDto();
        status.setQueueId(queueId);
        status.setQueueNumber(queue.getQueueNumber());
        status.setNumberOfWaitingCustomers(waitingCustomers.size());

        return status;
    }

    @Override
    public List<CustomerQueueDto> getOrdersInQueue(UUID queueId) {
        Queue queue = queueRepository.findById(queueId)
                .orElseThrow(() -> new ResourceNotFoundException("Queue not found"));

        AtomicInteger positionCounter = new AtomicInteger(1);  // Start with 1
        return customerQueueRepository.findAllByQueueOrderByCreatedAtAsc(queue).stream().map(customerQueue -> {
            CustomerQueueDto dto = new CustomerQueueDto();
            dto.setOrderId(customerQueue.getId());
            dto.setOrderNumber(customerQueue.getOrder().getOrderNumber());
            dto.setQueuePosition(positionCounter.getAndIncrement());
            dto.setCustomerName(customerQueue.getCustomer().getFirstName());
            dto.setCustomerPhone(customerQueue.getCustomer().getMobileNumber());
            dto.setCustomerQueueId(customerQueue.getId());

            List<OrderItemDto> orderItemDtoList = customerQueue.getOrder().getOrderItems()
                    .stream().map(item -> OrderItemDto.builder()
                            .name(item.getMenuItem().getName())
                            .quantity(item.getQuantity())
                            .build())
                    .collect(Collectors.toList());
            dto.setOrderItemList(orderItemDtoList);
            return dto;
        }).collect(Collectors.toList());

    }

    @Override
    public List<QueueDto> getQueuesByFranchise(Franchise franchise) {
        return queueRepository.findAllByFranchise(franchise).stream()
                .map(queue -> QueueDto.builder()
                    .id(queue.getId())
                    .queueNumber(queue.getQueueNumber())
                    .build()
                ).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void serviceCustomer(UUID queueId, UUID customerQueueId) {
        Queue queue = queueRepository.findById(queueId)
                .orElseThrow(() -> new ResourceNotFoundException("Queue not found"));

        CustomerQueue customerQueue = customerQueueRepository.findById(customerQueueId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not in queue"));

        // Mark order status
        Order order = customerQueue.getOrder();
        order.setStatus(OrderStatus.SUCCESS);
        orderRepository.save(order);
        log.info("[serviceQueue: {}] - Mark order status: {}", queueId, order.getId());

        // Remove customer from queue
        queue.getCustomerQueues().remove(customerQueue);
        customerQueueRepository.delete(customerQueue);
        log.info("[serviceQueue: {}] - Removed customer from queue", queueId);

        // Find and update the customer score
        log.info("[serviceQueue: {}] - Find and update the customer score: {}, ", queueId, order.getCustomer());
        CustomerScore customerScore = customerScoreRepository.findByCustomer(order.getCustomer()).orElse(null);
        if (customerScore == null) {
            customerScore = new CustomerScore(order.getCustomer(), order.getFranchise());
        }
        customerScore.setScore(customerScore.getScore() + 1);
        customerScoreRepository.save(customerScore);

    }

    @Override
    public Queue addOrderToQueue(Order order, User customer) {
        // Find the available queue for the franchise
        Queue queue = findAvailableQueue(order.getFranchise());
        if (queue == null) {
            throw new ResourceNotFoundException("No available queue for the franchise");
        }

        // Create a new CustomerQueue entry
        CustomerQueue customerQueue = new CustomerQueue();
        customerQueue.setCustomer(customer);
        customerQueue.setQueue(queue);
        customerQueue.setWaitingSince(LocalDateTime.now());
        customerQueue.setOrder(order);
        customerQueueRepository.save(customerQueue);

        return queue;
    }

    @Override
    public int getCustomerQueueSize(Queue queue) {
        List<CustomerQueue> customerQueueList = customerQueueRepository.findAllByQueueOrderByCreatedAtAsc(queue);
        return customerQueueList.size();
    }

    @Override
    public void removeOrderFromQueue(Queue queue, Order order) {
        // Find and delete the CustomerQueue entry
        Optional<CustomerQueue> customerQueueOptional = customerQueueRepository.findByOrder(order);
        if (customerQueueOptional.isPresent()) {
            CustomerQueue customerQueue = customerQueueOptional.get();
            customerQueueRepository.delete(customerQueue);
        } else {
            throw new ResourceNotFoundException("Custome not found in queue");
        }
    }

    private Queue findAvailableQueue(Franchise franchise) {
        // Find the first available queue for the franchise
        List<Queue> queueList = queueRepository.findAllByFranchise(franchise);
        return queueList.stream()
                .filter(queue -> this.getCustomerQueueSize(queue) < franchise.getMaxQueueSize())
                .findFirst()
                .orElse(null);
    }
}
