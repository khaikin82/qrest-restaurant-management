package com.khaikin.qrest.food;

import com.khaikin.qrest.exception.ResourceNotFoundException;
import com.khaikin.qrest.util.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepository;
    private final FileStorageService fileStorageService;
    private final String uploadDir = "images/food";

    @Override
    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }

    @Override
    public Food getFoodById(Long id) {
        return foodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food", "foodId", id));
    }

    @Override
    public Food createFood(Food food) {
        return foodRepository.save(food);
    }

    @Override
    public Food updateFood(Long id, Food food) {
        Food existingFood = foodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food", "foodId", id));

        existingFood.setName(food.getName());
        existingFood.setDescription(food.getDescription());
        existingFood.setPrice(food.getPrice());
        existingFood.setQuantity(food.getQuantity());
        existingFood.setCategory(food.getCategory());
        existingFood.setImageUrl(food.getImageUrl());

        return foodRepository.save(existingFood);
    }

    @Override
    public Food createFood(Food food, MultipartFile imageFile, HttpServletRequest request)
            throws IOException {
        String imageUrl = fileStorageService.storeFile(imageFile, uploadDir, request);

        food.setImageUrl(imageUrl);
        return foodRepository.save(food);
    }

    @Override
    public Food updateFood(Long id, Food food, MultipartFile updateImageFile, HttpServletRequest request)
            throws IOException {
        Food existingFood = foodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food", "foodId", id));

        existingFood.setName(food.getName());
        existingFood.setDescription(food.getDescription());
        existingFood.setPrice(food.getPrice());
        existingFood.setQuantity(food.getQuantity());
        existingFood.setCategory(food.getCategory());
        existingFood.setImageUrl(food.getImageUrl());

        String imageUrl = fileStorageService.storeFile(updateImageFile, uploadDir, request);

        existingFood.setImageUrl(imageUrl);

        return foodRepository.save(existingFood);
    }


    @Override
    public void deleteFoodById(Long id) {
        Food existingFood = foodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food", "foodId", id));

        foodRepository.delete(existingFood);
    }
}
