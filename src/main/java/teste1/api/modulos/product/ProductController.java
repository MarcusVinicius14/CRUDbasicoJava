package teste1.api.modulos.product;


import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepository repository;

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

    @GetMapping("/{id}/image")
    @Transactional
    public ResponseEntity getproductimage (@PathVariable UUID id) {
        var Product = repository.findById(id);

        return ResponseEntity.ok().contentType(MediaType.valueOf(Product.get().getImageType())).body(Product.get().getImageData());
    }

    @PostMapping
    @Transactional
    public ResponseEntity registerProduct(
          @Valid  @RequestParam("name") String name,
          @Valid  @RequestParam("price_in_cents") int priceInCents,
          @Valid  @RequestParam("image") MultipartFile imageFile) throws IOException {

        Product newProduct = new Product();
        newProduct.setName(name);
        newProduct.setPrice_in_cents(priceInCents);

        newProduct.setImageData(imageFile.getBytes());
        newProduct.setImageType(imageFile.getContentType());

        repository.save(newProduct);

        return ResponseEntity.status(201).body(newProduct);

    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updateProduct(
            @PathVariable UUID id,
            @Valid  @RequestParam("name") String name,
            @Valid  @RequestParam("price_in_cents") int priceInCents,
            @Valid  @RequestParam("image") MultipartFile imageFile) throws IOException {

        Product product = repository.getReferenceById(id);

        product.setName(name);
        product.setPrice_in_cents(priceInCents);

        product.setImageData(imageFile.getBytes());
        product.setImageType(imageFile.getContentType());

        return ResponseEntity.ok(new RequestProduct(product));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteProduct( @PathVariable UUID id) {

        Product product = repository.getReferenceById(id);

        repository.delete(product);
        return ResponseEntity.status(204).build();

    }

}
