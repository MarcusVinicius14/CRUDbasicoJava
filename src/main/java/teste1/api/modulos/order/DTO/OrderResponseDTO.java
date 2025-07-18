package teste1.api.modulos.order.DTO;

import teste1.api.modulos.order.orderItem.DTO.OrderItemResponseDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderResponseDTO(
        UUID orderId,
        LocalDateTime orderDate,
        List<OrderItemResponseDTO> items
) {}