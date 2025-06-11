package br.edu.ibmec.cloud.Ecommerce.controllers;

import br.edu.ibmec.cloud.Ecommerce.dtos.order.CreateOrderDto;
import br.edu.ibmec.cloud.Ecommerce.dtos.order.OrderDto;
import br.edu.ibmec.cloud.Ecommerce.models.Order;
import br.edu.ibmec.cloud.Ecommerce.useCases.order.GetOrdersByUserUseCase;
import br.edu.ibmec.cloud.Ecommerce.useCases.order.ListOrdersUseCase;
import br.edu.ibmec.cloud.Ecommerce.useCases.order.PlaceOrderUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final PlaceOrderUseCase placeOrderUseCase;
    private final ListOrdersUseCase listOrdersUseCase;
    private final GetOrdersByUserUseCase getOrdersByUserUseCase;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody @Valid CreateOrderDto dto) {
        try {
            Order order = placeOrderUseCase.execute(dto);
            return ResponseEntity.ok(order);
        } catch (IllegalArgumentException | IllegalStateException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Unexpected error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        return ResponseEntity.ok(listOrdersUseCase.execute());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getOrdersByUser(@PathVariable String userId) {
        try {
            List<OrderDto> orders = getOrdersByUserUseCase.execute(userId);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to fetch orders: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
}
