package teste1.api.modulos.order.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import teste1.api.modulos.order.orderItem.DTO.OrderItemDTO;

import java.util.List;

public record CreateOrderDTO(
        @NotEmpty
        @Valid
        List<OrderItemDTO> items) {
}
