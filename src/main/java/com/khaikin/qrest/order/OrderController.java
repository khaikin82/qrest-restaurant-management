package com.khaikin.qrest.order;

import com.khaikin.qrest.foodorder.FoodOrder;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Validated
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/completed")
    public ResponseEntity<List<Order>> getCompletedOrders() {
        return ResponseEntity.ok(orderService.getCompletedOrders());
    }

    @GetMapping("/completed/without-payment")
    public ResponseEntity<List<Order>> getCompletedOrdersWithoutPayment() {
        return ResponseEntity.ok(orderService.getCompletedOrdersWithoutPayment());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest orderRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(orderRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable @Positive Long id, @RequestBody Order order) {
        return ResponseEntity.ok(orderService.updateOrder(id, order));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id,
                                                   @RequestBody OrderStatus orderStatus) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, orderStatus));
    }

    @PatchMapping("/foods/{foodOrderId}/status/{isCompleted}")
    public ResponseEntity<FoodOrder> updateFoodOrderStatus(@PathVariable Long foodOrderId,
                                                           @PathVariable boolean isCompleted) {
        return ResponseEntity.ok(orderService.updateFoodOrderStatus(foodOrderId, isCompleted));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderById(@PathVariable @Positive Long id) {
        orderService.deleteOrderById(id);
        return ResponseEntity.noContent().build();
    }
}