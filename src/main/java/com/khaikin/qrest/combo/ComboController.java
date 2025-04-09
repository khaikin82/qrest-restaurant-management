package com.khaikin.qrest.combo;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/combos")
@RequiredArgsConstructor
@Validated
public class ComboController {
    private final ComboService comboService;

    @GetMapping
    public ResponseEntity<List<Combo>> getAllCombos() {
        return ResponseEntity.ok(comboService.getAllCombos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Combo> getComboById(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(comboService.getComboById(id));
    }

    @PostMapping
    public ResponseEntity<Combo> createCombo(@RequestBody ComboRequest comboRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(comboService.createCombo(comboRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Combo> updateCombo(@PathVariable @Positive Long id, @RequestBody Combo combo) {
        return ResponseEntity.ok(comboService.updateCombo(id, combo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComboById(@PathVariable @Positive Long id) {
        comboService.deleteComboById(id);
        return ResponseEntity.noContent().build();
    }
}