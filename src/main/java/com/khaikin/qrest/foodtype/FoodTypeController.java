package com.khaikin.qrest.foodtype;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/food-types")
@RequiredArgsConstructor
public class FoodTypeController {
    private final FoodTypeService foodTypeService;

    @GetMapping
    public ResponseEntity<List<FoodType>> getAllFoodTypes() {
        return ResponseEntity.ok(foodTypeService.getAllFoodTypes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodType> getFoodTypeById(@PathVariable Integer id) {
        return ResponseEntity.ok(foodTypeService.getFoodTypeById(id));
    }

    @PostMapping
    public ResponseEntity<FoodType> createFoodType(@RequestBody FoodType foodType) {
        return ResponseEntity.ok(foodTypeService.createFoodType(foodType));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FoodType> updateFoodType(@PathVariable Integer id, @RequestBody FoodType foodType) {
        return ResponseEntity.ok(foodTypeService.updateFoodType(id, foodType));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFoodType(@PathVariable Integer id) {
        foodTypeService.deleteFoodType(id);
        return ResponseEntity.noContent().build();
    }
}
