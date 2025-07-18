package teste1.api.modulos.order.orderItem.DTO;

import java.util.UUID;

public record OrderItemResponseDTO(
        UUID itemId,
        UUID productId,
        String productName,
        int quantity,
        int pricePerUnit
) {}