package com.khaikin.qrest.food;

import com.khaikin.qrest.payloads.ApiResponse;

import java.util.List;

public interface FoodService {
    List<Food> getAllFoods();

    Food getFoodById(Integer id);

    Food createFood(Food food);

    Food updateFood(Integer id, Food updateFood);

    ApiResponse deleteFoodById(Integer id);
}
