package com.khaikin.qrest.food;

import com.khaikin.qrest.payloads.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/foods")
@RequiredArgsConstructor
public class FoodController {
    private final FoodService foodService;

    @GetMapping
    public ResponseEntity<List<Food>> getAllFoods() {
        List<Food> foods = foodService.getAllFoods();
        return ResponseEntity.ok(foods);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Food> getFoodById(@PathVariable Integer id) {
        Food food = foodService.getFoodById(id);
        return ResponseEntity.ok(food);
    }

    @PostMapping
    public Food createFood(@RequestBody Food food) {
        return foodService.createFood(food);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Food> updateFood(@PathVariable Integer id, @RequestBody Food updateFood) {
        Food updatedFood = foodService.updateFood(id, updateFood);
        return ResponseEntity.ok(updatedFood);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteFoodById(@PathVariable Integer id) {
        ApiResponse apiResponse = foodService.deleteFoodById(id);
        return ResponseEntity.ok(apiResponse);
    }
}
