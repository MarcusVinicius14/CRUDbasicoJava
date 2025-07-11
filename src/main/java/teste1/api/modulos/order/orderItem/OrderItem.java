package teste1.api.modulos.order.orderItem;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import teste1.api.modulos.order.Orders;
import teste1.api.modulos.product.Product;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "order_items")
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID id;

    private Integer quantity;
    private Integer pricePerUnit;


    // Relação com Order: Muitos itens pertencem a UM Order.
    @ManyToOne
    @JoinColumn(name = "order_id") // Nome da coluna da chave estrangeira no banco
    @JsonBackReference
    private Orders order;

    // Relação com Product: Muitos itens podem apontar para UM produto.
    @ManyToOne
    @JoinColumn(name = "product_id") // Nome da coluna da chave estrangeira no banco
    private Product product;

}
