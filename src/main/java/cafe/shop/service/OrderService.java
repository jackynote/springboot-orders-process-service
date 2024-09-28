package cafe.shop.service;

import cafe.shop.model.dto.OrderDetailDto;
import cafe.shop.model.dto.OrderDto;
import cafe.shop.model.dto.OrderRequest;
import cafe.shop.model.dto.QueuePositionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface OrderService {

    Page<OrderDto> getOrderListByCustomer(Map<String, Object> params, Pageable pageable);

    OrderDto placeOrder(OrderRequest request);

    QueuePositionDto getQueuePosition(UUID orderId);

    OrderDetailDto getOrderDetail(UUID orderId);

    void cancelOrder(UUID orderId);
}
