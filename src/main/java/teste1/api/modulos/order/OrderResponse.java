package teste1.api.modulos.order;

import teste1.api.modulos.order.orderItem.OrderItemResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderResponse(
        UUID orderId,
        LocalDateTime orderDate,
        List<OrderItemResponse> items
) {}