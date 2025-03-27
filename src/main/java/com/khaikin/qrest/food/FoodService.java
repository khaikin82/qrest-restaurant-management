package com.khaikin.qrest.food;


import java.util.List;

public interface FoodService {
    List<Food> getAllFoods();

    Food getFoodById(Long id);

    Food createFood(Food food);

    Food updateFood(Long id, Food food);

    void deleteFoodById(Long id);
}
