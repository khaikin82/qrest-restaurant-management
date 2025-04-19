package com.khaikin.qrest.food;


import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FoodService {
    List<Food> getAllFoods();

    Food getFoodById(Long id);

    Food createFood(Food food);

    Food updateFood(Long id, Food food);

    public Food createFood(Food food, MultipartFile imageFile)
            throws IOException;

    public Food updateFood(Long id, Food food, MultipartFile updateImageFile)
            throws IOException;

    void deleteFoodById(Long id);
}
