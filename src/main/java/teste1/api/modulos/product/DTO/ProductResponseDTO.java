package teste1.api.modulos.product.DTO;


import java.util.UUID;

public record ProductResponseDTO(
        UUID id,
        String name,
        int price_in_cents,
        String imageUrl
) {}