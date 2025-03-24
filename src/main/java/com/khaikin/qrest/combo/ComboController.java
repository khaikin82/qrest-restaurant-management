package com.khaikin.qrest.combo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/combos")
@RequiredArgsConstructor
public class ComboController {
    private final ComboService comboService;

    @GetMapping
    public ResponseEntity<List<Combo>> getAllCombos() {
        return ResponseEntity.ok(comboService.getAllCombos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Combo> getComboById(@PathVariable Integer id) {
        return ResponseEntity.ok(comboService.getComboById(id));
    }

    @PostMapping
    public ResponseEntity<Combo> createCombo(@RequestBody Combo combo) {
        return ResponseEntity.ok(comboService.createCombo(combo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Combo> updateCombo(@PathVariable Integer id, @RequestBody Combo combo) {
        return ResponseEntity.ok(comboService.updateCombo(id, combo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCombo(@PathVariable Integer id) {
        comboService.deleteCombo(id);
        return ResponseEntity.noContent().build();
    }
}