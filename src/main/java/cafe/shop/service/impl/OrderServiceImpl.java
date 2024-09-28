package cafe.shop.service.impl;

import cafe.shop.common.GenericSpecification;
import cafe.shop.exception.CustomerAlreadyExistsException;
import cafe.shop.exception.FranchiseNotFoundException;
import cafe.shop.exception.ResourceNotFoundException;
import cafe.shop.model.constant.OrderStatus;
import cafe.shop.model.dto.*;
import cafe.shop.model.entities.*;
import cafe.shop.repository.FranchiseRepository;
import cafe.shop.repository.MenuItemRepository;
import cafe.shop.repository.OrderRepository;
import cafe.shop.repository.UserRepository;
import cafe.shop.service.BaseService;
import cafe.shop.service.OrderService;
import cafe.shop.service.QueueService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Log4j2
public class OrderServiceImpl extends BaseService implements OrderService {


    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private QueueService queueService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FranchiseRepository franchiseRepository;

    @Override
    public Page<OrderDto> getOrderListByCustomer(Map<String, Object> params, Pageable pageable) {
        String customerId = currentUser().getId();
        userRepository.findById(UUID.fromString(customerId)).ifPresent(customer -> params.put("customer", customer));

        Specification<Order> spec = new GenericSpecification<>(params, pageable);
        Page<Order> orderPage = orderRepository.findAll(spec, pageable);

        return orderPage.map(order -> OrderDto
                .builder()
                .orderId(order.getId())
                .orderNumber(order.getOrderNumber())
                .orderStatus(order.getStatus())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build()
        );
    }

    @Override
    @Transactional
    public OrderDto placeOrder(OrderRequest request) {

        // Fetch franchise and user
        Franchise franchise = franchiseRepository.findById(request.getFranchiseId())
                .orElseThrow(() -> new FranchiseNotFoundException("Franchise not found"));
        User user = userRepository.findById(UUID.fromString(currentUser().getId()))
                .orElseThrow(() -> new CustomerAlreadyExistsException("User not found"));

        // Create and save the order
        Order order = new Order();
        order.setCustomer(user);
        order.setFranchise(franchise);
        order.setOrderNumber(generateOrderNumber());
        order.setStatus(OrderStatus.NEW);
        orderRepository.save(order);

        // Add items to the order
        log.info("[processOrder: {}] - Created new order", order.getId());
        List<OrderItem> orderItems = request.getItems().stream().map(itemRequest -> {
            MenuItem menuItem = menuItemRepository.findById(itemRequest.getMenuItemId())
                    .orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));
            OrderItem orderItem = new OrderItem();
            orderItem.setMenuItem(menuItem);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setOrder(order);
            return orderItem;
        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);
        orderRepository.save(order);

        log.info("[processOrder: {}] - Added menu {} items to order", order.getId(), orderItems.size());

        // Add order to queue
        Queue queue = queueService.addOrderToQueue(order, user);
        order.setQueue(queue);
        order.setStatus(OrderStatus.PROCESS);
        orderRepository.save(order);
        log.info("[processOrder: {}] - Added order to queue {}", order.getId(), queue.getId());

        int queuePosition = queueService.getCustomerQueueSize(queue);
        int waitingTime = calculateExpectedWaitingTime(franchise);


        return OrderDto.builder()
                .orderId(order.getId())
                .orderNumber(order.getOrderNumber())
                .queuePosition(queuePosition)
                .expectedWaitingTime(waitingTime)
                .build();
    }

    @Override
    public QueuePositionDto getQueuePosition(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        Queue queue = order.getQueue();
        int queuePosition = queueService.getCustomerQueueSize(queue);
        int waitingTime = calculateExpectedWaitingTime(order.getFranchise());

        return new QueuePositionDto(queuePosition, waitingTime);
    }

    @Override
    public OrderDetailDto getOrderDetail(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        OrderDetailDto dto =  OrderDetailDto.builder()
                .orderId(order.getId())
                .orderNumber(order.getOrderNumber())
                .customerName(order.getCustomer().getFirstName())
                .customerPhone(order.getCustomer().getMobileNumber())
                .status(order.getStatus())
                .build();

        List<OrderItemDto> orderItemDtoList = order.getOrderItems()
                .stream().map(item -> OrderItemDto.builder()
                        .name(item.getMenuItem().getName())
                        .quantity(item.getQuantity())
                        .build())
                .collect(Collectors.toList());
        dto.setOrderItemList(orderItemDtoList);

        return dto;
    }

    @Override
    public void cancelOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        Queue queue = order.getQueue();

        queueService.removeOrderFromQueue(queue, order);
        log.info("[processCancelOrder: {}] - Removed order in queue {}", order.getId(), queue.getId());

        order.setStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
        log.info("[processCancelOrder: {}] - Mark cancel order", order.getId());
    }

    private int calculateExpectedWaitingTime(Franchise franchise) {
        // Logic to calculate waiting time based on franchise queue length and other factors
        // For example, assume a constant waiting time for simplicity
        return 15; // Example waiting time in minutes
    }

    private String generateOrderNumber() {
        // Generate a unique order number
        return "O-" + RandomStringUtils.randomAlphanumeric(12).toUpperCase();
    }

}
