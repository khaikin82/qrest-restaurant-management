package com.khaikin.qrest.food;

import com.khaikin.qrest.exception.ConflictException;
import com.khaikin.qrest.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/foods")
@RequiredArgsConstructor
@Validated
public class FoodController {
    private final FoodService foodService;

    @GetMapping
    public ResponseEntity<List<Food>> getAllFoods() {
        List<Food> foods = foodService.getAllFoods();
        return ResponseEntity.ok(foods);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Food> getFoodById(@PathVariable @Positive Long id) {
        Food food = foodService.getFoodById(id);
        return ResponseEntity.ok(food);
    }

    @PostMapping
    public ResponseEntity<Food> createFood(@RequestBody Food food) {
        Food createdFood = foodService.createFood(food);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFood);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Food> updateFood(@PathVariable @Positive Long id, @RequestBody Food food) {
        Food updatedFood = foodService.updateFood(id, food);
        return ResponseEntity.ok(updatedFood);
    }


    @PostMapping("/with-image")
    public ResponseEntity<Food> createFood(@RequestPart Food food, @RequestPart MultipartFile imageFile, HttpServletRequest request) {
        try {
            Food newFood = foodService.createFood(food, imageFile, request);
            return new ResponseEntity<>(newFood, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ConflictException("Conflict Error: Create Food with Image");
        }
    }

    @PutMapping("/with-image/{id}")
    public ResponseEntity<Food> updateFood(@PathVariable Long id, @RequestPart Food food,
                                           @RequestPart MultipartFile imageFile, HttpServletRequest request) {
        try {
            Food newFood = foodService.updateFood(id, food, imageFile, request);
            return new ResponseEntity<>(newFood, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("food", "foodId", id);
        } catch (Exception e) {
            throw new ConflictException("Conflict Error: Update Food");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFoodById(@PathVariable @Positive Long id) {
        foodService.deleteFoodById(id);
        return ResponseEntity.noContent().build();
    }
}
