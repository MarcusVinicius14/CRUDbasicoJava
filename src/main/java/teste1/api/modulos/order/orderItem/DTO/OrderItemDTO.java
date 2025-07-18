package teste1.api.modulos.order.orderItem.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record OrderItemDTO(@NotNull UUID productId, @NotNull @Min(1) int quantity){

}
