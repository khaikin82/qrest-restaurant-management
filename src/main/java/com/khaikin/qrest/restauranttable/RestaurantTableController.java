package com.khaikin.qrest.restauranttable;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurant_tables")
@RequiredArgsConstructor
@Validated
public class RestaurantTableController {
    private final RestaurantTableService restaurantTableService;

    @GetMapping
    public ResponseEntity<List<RestaurantTable>> getAllTables() {
        List<RestaurantTable> restaurantTables = restaurantTableService.getAllTables();
        return ResponseEntity.ok(restaurantTables);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantTable> getTableById(@PathVariable @Positive Long id) {
        RestaurantTable restaurantTable = restaurantTableService.getTableById(id);
        return ResponseEntity.ok(restaurantTable);
    }

    @PostMapping
    public ResponseEntity<RestaurantTable> createTable(@RequestBody RestaurantTable restaurantTable) {
        RestaurantTable createdRestaurantTable = restaurantTableService.createTable(restaurantTable);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRestaurantTable);
    }

    // API nhận yêu cầu tạo nhiều bàn
    @PostMapping("/create-multiple")
    public ResponseEntity<List<RestaurantTable>> createMultipleTables(@RequestBody MultipleTableRequestDto tableRequest) {
        List<RestaurantTable> createdTables = restaurantTableService.createMultipleTables(tableRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTables);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantTable> updateTable(@PathVariable @Positive Long id, @RequestBody RestaurantTable restaurantTable) {
        RestaurantTable updatedRestaurantTable = restaurantTableService.updateTable(id, restaurantTable);
        return ResponseEntity.ok(updatedRestaurantTable);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTableById(@PathVariable @Positive Long id) {
        restaurantTableService.deleteTableById(id);
        return ResponseEntity.noContent().build();
    }
}
