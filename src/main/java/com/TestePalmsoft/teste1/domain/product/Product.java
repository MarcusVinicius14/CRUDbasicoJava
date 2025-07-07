package com.TestePalmsoft.teste1.domain.product;


import jakarta.persistence.*;
import lombok.*;

@Table(name="product")
@Entity(name="product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Product {


    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    private int price_in_cents;

    public Product(RequestProduct data) {
        this.name = data.name();
        this.price_in_cents = data.price_in_cents();
    }

}
