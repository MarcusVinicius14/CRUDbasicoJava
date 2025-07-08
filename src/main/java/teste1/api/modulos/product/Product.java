package teste1.api.modulos.product;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;


@Table(name="product")
@Entity(name="product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID id;

    private String name;

    private int price_in_cents;

    @Lob
    private byte[] imageData;

    private String imageType;

    public Product(RequestProduct data) {
        this.name = data.name();
        this.price_in_cents = data.price_in_cents();
    }

}
