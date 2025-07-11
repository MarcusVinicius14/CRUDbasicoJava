package teste1.api.modulos.order;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teste1.api.modulos.order.orderItem.DTOorderItem;
import teste1.api.modulos.order.orderItem.OrderItem;
import teste1.api.modulos.order.orderItem.OrderItemResponse;
import teste1.api.modulos.product.Product;
import teste1.api.modulos.product.ProductRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @PostMapping
    @Transactional
    public ResponseEntity createOrder(@RequestBody @Valid DTOcreateOrder request) {

        Orders newOrder = new Orders();

        // 2. Itera sobre os itens da requisição para criar os OrderItems
        for (DTOorderItem itemRequest : request.items()) {
            // Busca o produto no banco de dados
            Product product = productRepository.findById(itemRequest.productId())
                    .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID: " + itemRequest.productId()));

            // Cria o novo item do pedido
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemRequest.quantity());
            orderItem.setPricePerUnit(product.getPrice_in_cents());
            orderItem.setOrder(newOrder);

            // Adiciona o item à lista da ordem
            newOrder.getItems().add(orderItem);
        }

        // 3. Salva a Order (graças ao CascadeType.ALL, os OrderItems são salvos juntos)
        Orders savedOrder = orderRepository.save(newOrder);

        return ResponseEntity.status(201).body(savedOrder);
    }

    @GetMapping
    public ResponseEntity getAllOrders() {
        List<Orders> orders = orderRepository.findAll();

        List<OrderResponse> responseDtos = orders.stream().map(order -> {
            List<OrderItemResponse> itemResponses = order.getItems().stream()
                    .map(item -> new OrderItemResponse(
                            item.getId(),
                            item.getProduct().getId(),
                            item.getProduct().getName(),
                            item.getQuantity(),
                            item.getPricePerUnit()
                    ))
                    .collect(Collectors.toList());

            return new OrderResponse(
                    order.getId(),
                    order.getOrderDate(),
                    itemResponses
            );
        }).collect(Collectors.toList());

        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping("/{id}")
    public  ResponseEntity GetOrder(@PathVariable UUID id) {

        Orders order = orderRepository.getById(id);

        List<OrderItemResponse> itemResponses = order.getItems().stream()
                .map(item -> new OrderItemResponse(
                        item.getId(),
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getPricePerUnit()
                ))
                .collect(Collectors.toList());

        OrderResponse responseDto = new OrderResponse(
                order.getId(),
                order.getOrderDate(),
                itemResponses
        );

        return ResponseEntity.ok(responseDto);

    }


}
