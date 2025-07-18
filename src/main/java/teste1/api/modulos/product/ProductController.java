package teste1.api.modulos.product;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import teste1.api.Validation.ValidImage;
import teste1.api.modulos.Images.Image;
import teste1.api.modulos.Images.ImageRepository;
import teste1.api.modulos.product.DTO.ProductResponseDTO;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController // @RestController combina o comportamento do @Controller e do @ResponseBody.
@RequestMapping("/product") // responsavel por definir o end point do controller
@Validated
public class ProductController {

    @Value("${file.upload-dir}")
    private String uploadDir;



    //@Autowired é usada em Spring ‘Framework’ para indicar que uma dependência deve
    // ser automaticamente injetada. Ela pode ser usada em construtores, campos, métodos ou parâmetros.
    @Autowired
    private ProductRepository repository;

    @Autowired
    private ImageRepository imageRepository;

    @GetMapping
    @Transactional
    public ResponseEntity getAllProducts() {

        var allProducts = repository.findAll();

        return ResponseEntity.ok(allProducts);
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity getProduct(@PathVariable UUID id) {
        var Product = repository.findById(id);
        return ResponseEntity.ok(Product);
    }

    @GetMapping("/images/{id}")
    public ResponseEntity<Resource> serveFile(@PathVariable UUID id) throws MalformedURLException {

        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Imagem não encontrada: "));


        java.nio.file.Path filePath = java.nio.file.Paths.get(uploadDir).resolve(image.getImagePath()).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        // Verifica se o arquivo físico realmente existe.
        if (!resource.exists() || !resource.isReadable()) {
            throw new RuntimeException("Falha ao carregar o arquivo físico da imagem: ");
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getImageType()))
                .body(resource);
    }



    @PostMapping
    @Transactional
    public ResponseEntity registerProduct(
            @RequestParam("name") String name,
            @RequestParam("price_in_cents") Integer priceInCents,
            @ValidImage @RequestParam(value = "image", required = false) MultipartFile imageFile) throws IOException {

        Product newProduct = new Product();
        newProduct.setName(name);
        newProduct.setPrice_in_cents(priceInCents);

        // Verifica se um arquivo de imagem foi enviado
        if (imageFile != null && !imageFile.isEmpty()) {
            // Salva o arquivo no disco e obtém o nome único
            String filename = saveImageToFileSystem(imageFile);


            Image newImage = new Image();

            // Usa o setİmagePath no objeto 'newImage'
            newImage.setImagePath(filename);
            newImage.setImageType(imageFile.getContentType());


            newProduct.setImage(newImage);
        }

        Product savedProduct = repository.save(newProduct);


        String imageUrl = null;
        if (savedProduct.getImage() != null && savedProduct.getImage().getImagePath() != null) {
            imageUrl = "/product/images/" + savedProduct.getImage().getImagePath();
        }

        ProductResponseDTO responseDto = new ProductResponseDTO(
                savedProduct.getId(),
                savedProduct.getName(),
                savedProduct.getPrice_in_cents(),
                imageUrl
        );

        return ResponseEntity.status(201).body(responseDto);
    }

    // arquiva a imagem na pasta uploads e cria um filename unico com o UUID
    private String saveImageToFileSystem(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uniqueFilename = UUID.randomUUID().toString() + extension;

        Path filePath = uploadPath.resolve(uniqueFilename);
        Files.copy(file.getInputStream(), filePath);

        return uniqueFilename;
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updateProduct(
            @PathVariable UUID id,
            @RequestParam("name") String name,
            @RequestParam("price_in_cents") int priceInCents,
            @ValidImage @RequestParam(value = "image", required = false) MultipartFile imageFile) throws IOException {

        Product product = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID: " + id));

        product.setName(name);
        product.setPrice_in_cents(priceInCents);

        // Verifica se um novo arquivo de imagem foi enviado.
        if (imageFile != null && !imageFile.isEmpty()) {
            // Se já existe uma imagem antiga, apaga o arquivo físico do disco.
            if (product.getImage() != null && product.getImage().getImagePath() != null) {
                deleteImageFromFileSystem(product.getImage().getImagePath());
            }

            // Salva o novo arquivo no disco e obtém o nome único.
            String uniqueFilename = saveImageToFileSystem(imageFile);

            // Atualiza a entidade Image associada.
            Image image = product.getImage() != null ? product.getImage() : new Image();
            image.setImagePath(uniqueFilename);
            image.setImageType(imageFile.getContentType());
            product.setImage(image);
        }

        // Constrói a URL da imagem para o DTO de resposta.
        String imageUrl = null;
        if (product.getImage() != null && product.getImage().getImagePath() != null) {
            imageUrl = "/product/images/" + product.getImage().getImagePath();
        }

        // DTO
        ProductResponseDTO responseDto = new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getPrice_in_cents(),
                imageUrl
        );

        return ResponseEntity.ok(responseDto);
    }



    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteProductPhysically(@PathVariable UUID id) throws IOException {

        Product product = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID: " + id));

        // Verifica se existe uma imagem associada para ser deletada.
        if (product.getImage() != null && product.getImage().getImagePath() != null) {
            deleteImageFromFileSystem(product.getImage().getImagePath());
        }

        repository.delete(product);

        return ResponseEntity.noContent().build();
    }


    private void deleteImageFromFileSystem(String filename) throws IOException {
        if (filename == null || filename.isBlank()) {
            return;
        }
        try {
            java.nio.file.Path filePath = java.nio.file.Paths.get(uploadDir).resolve(filename);
            java.nio.file.Files.deleteIfExists(filePath);
        } catch (IOException e) {
            System.err.println("Erro ao deletar o arquivo de imagem: " + filename);
        }
    }

}
