package com.khaikin.qrest.comboorder;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/combo-orders")
@RequiredArgsConstructor
@Validated
public class ComboOrderController {
    private final ComboOrderService comboOrderService;

    @GetMapping
    public ResponseEntity<List<ComboOrder>> getAllComboOrders() {
        return ResponseEntity.ok(comboOrderService.getAllComboOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComboOrder> getComboOrderById(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(comboOrderService.getComboOrderById(id));
    }

    @PostMapping
    public ResponseEntity<ComboOrder> createComboOrder(@RequestBody ComboOrder comboOrder) {
        return ResponseEntity.status(HttpStatus.CREATED).body(comboOrderService.createComboOrder(comboOrder));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComboOrder> updateComboOrder(@PathVariable @Positive Long id, @RequestBody ComboOrder comboOrder) {
        return ResponseEntity.ok(comboOrderService.updateComboOrder(id, comboOrder));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComboOrderById(@PathVariable @Positive Long id) {
        comboOrderService.deleteComboOrderById(id);
        return ResponseEntity.noContent().build();
    }
}