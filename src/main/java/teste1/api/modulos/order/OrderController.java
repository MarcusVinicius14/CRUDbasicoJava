package teste1.api.modulos.order;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderRepository repository;

    @GetMapping
    public ResponseEntity getAllOrders() {

        var allOrders = repository.findAll();

        return ResponseEntity.ok(allOrders);
    }

}
