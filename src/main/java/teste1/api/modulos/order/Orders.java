package teste1.api.modulos.order;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name= "orders")
@Entity(name= "orders")
@EqualsAndHashCode(of="id")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID id;

    private int quantity;

    private UUID product_id;

    private Date order_date;
}
