package teste1.api.modulos.product.DTO;

import teste1.api.modulos.product.Product;

import java.util.UUID;

public record RequestProductDTO(
        UUID id,
        String name,
        int price_in_cents) {

    public RequestProductDTO(Product product) {
        this(product.getId(), product.getName(), product.getPrice_in_cents());
    }


}
