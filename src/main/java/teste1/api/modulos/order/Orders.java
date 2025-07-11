package teste1.api.modulos.order;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import teste1.api.modulos.order.orderItem.OrderItem;
import teste1.api.modulos.product.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    // Relação com OrderItem: UM pedido tem MUITOS itens.

    @JsonManagedReference // para formatar a resposta da requisição
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items = new ArrayList<>();

    @CreationTimestamp //cria automaticamente a data e horario da Order
    private LocalDateTime orderDate;


}
