package com.TestePalmsoft.teste1.controllers;


import com.TestePalmsoft.teste1.domain.product.Product;
import com.TestePalmsoft.teste1.domain.product.ProductRepository;
import com.TestePalmsoft.teste1.domain.product.RequestProduct;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping
    @Transactional
    public ResponseEntity registerProduct(@RequestBody @Valid RequestProduct data) {

        Product newProduct = new Product(data);

        repository.save(newProduct);
        System.out.println(data);
        return ResponseEntity.status(201).build();

    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updateProduct(@PathVariable String id, @RequestBody RequestProduct data) {
        // Aqui você pode continuar usando getReferenceById, pois a atualização
        // ocorrerá dentro de uma transação.
        Product product = repository.getReferenceById(id);

        product.setName(data.name());
        product.setPrice_in_cents(data.price_in_cents());

        // Retorna um DTO limpo e seguro para serialização, em vez da entidade/proxy
        return ResponseEntity.ok(new RequestProduct(product));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteProduct( @PathVariable String id) {

        Product product = repository.getReferenceById(id);

        repository.delete(product);
        return ResponseEntity.status(204).build();

    }

}
