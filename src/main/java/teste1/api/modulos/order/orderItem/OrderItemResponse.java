package teste1.api.modulos.order.orderItem;

import java.util.UUID;

public record OrderItemResponse(
        UUID itemId,
        UUID productId,
        String productName,
        int quantity,
        int pricePerUnit
) {}