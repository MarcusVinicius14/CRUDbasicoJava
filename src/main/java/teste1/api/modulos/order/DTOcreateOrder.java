package teste1.api.modulos.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import teste1.api.modulos.order.orderItem.DTOorderItem;

import java.util.List;

public record DTOcreateOrder (
        @NotEmpty
        @Valid
        List<DTOorderItem> items) {
}
