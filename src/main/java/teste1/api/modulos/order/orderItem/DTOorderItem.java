package teste1.api.modulos.order.orderItem;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record DTOorderItem(@NotNull UUID productId, @NotNull @Min(1) int quantity){

}
