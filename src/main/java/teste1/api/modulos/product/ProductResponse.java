package teste1.api.modulos.product;


import java.util.UUID;

public record ProductResponse(
        UUID id,
        String name,
        int price_in_cents,
        String imageUrl
) {}