package teste1.api.modulos.product;

import java.util.UUID;

public record RequestProduct(
        UUID id,
        String name,
        int price_in_cents) {

    public RequestProduct(Product product) {
        this(product.getId(), product.getName(), product.getPrice_in_cents());
    }

}
