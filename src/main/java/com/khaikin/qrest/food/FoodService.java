package com.khaikin.qrest.food;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FoodService {
    List<Food> getAllFoods();

    Food getFoodById(Long id);

    Food createFood(Food food);

    Food updateFood(Long id, Food food);

    Food createFood(Food food, MultipartFile imageFile, HttpServletRequest request)
            throws IOException;

    Food updateFood(Long id, Food food, MultipartFile updateImageFile, HttpServletRequest request)
            throws IOException;

    void deleteFoodById(Long id);
}
