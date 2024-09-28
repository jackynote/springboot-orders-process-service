package cafe.shop.controller;

import cafe.shop.common.PageRequestBuilder;
import cafe.shop.model.constant.OrderStatus;
import cafe.shop.model.dto.OrderDetailDto;
import cafe.shop.model.dto.OrderDto;
import cafe.shop.model.dto.OrderRequest;
import cafe.shop.model.dto.QueuePositionDto;
import cafe.shop.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * GET /api/v1/orders?page={page}&limit={size}&sort={sortField1}:{sortDirection1},{sortField2}:{sortDirection2}&status={status}
     */
    @GetMapping
    public ResponseEntity<Page<OrderDto>> getOrders(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(name = "sort", required = false) String sortString
            ) {
        PageRequest pageRequest = PageRequestBuilder.buildPageRequest(page - 1, limit, sortString);
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.isNotEmpty(status)) params.put("status", OrderStatus.valueOf(status));

        Page<OrderDto> response = orderService.getOrderListByCustomer(params, pageRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<OrderDto> placeOrder(@RequestBody OrderRequest request) {
        OrderDto response = orderService.placeOrder(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailDto> getOrderDetails(@PathVariable UUID orderId) {
        OrderDetailDto response = orderService.getOrderDetail(orderId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{orderId}/position")
    public ResponseEntity<QueuePositionDto> getQueuePosition(@PathVariable UUID orderId) {
        QueuePositionDto response = orderService.getQueuePosition(orderId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{orderId}/cancel")
    public ResponseEntity<Map<String, String>> cancelOrder(@PathVariable UUID orderId) {
        orderService.cancelOrder(orderId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Order cancelled successfully");
        return ResponseEntity.ok(response);
    }
}
