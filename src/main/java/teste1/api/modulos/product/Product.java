package teste1.api.modulos.product;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import teste1.api.modulos.Images.Image;

import java.util.UUID;


@Table(name="product") //relaciona a entidade com o nome da tabela no banco de dados
@Entity(name="product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.UUID) // garante no banco de dados que o tipo da coluna sera UUID
    private UUID id;

    private String name;

    private int price_in_cents;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;

    public Product(RequestProduct data) {
        this.name = data.name();
        this.price_in_cents = data.price_in_cents();
    }

}
