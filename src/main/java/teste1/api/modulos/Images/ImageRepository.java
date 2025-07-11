package teste1.api.modulos.Images;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional; // Importe Optional
import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {


    // Ele vai procurar na tabela 'images' por um registro que tenha o 'imagePath' correspondente.
    Optional<Image> findByImagePath(String imagePath);

}