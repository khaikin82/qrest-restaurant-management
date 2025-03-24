package com.khaikin.qrest.food;

import com.khaikin.qrest.exception.ResourceNotFoundException;
import com.khaikin.qrest.payloads.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepository;

    @Override
    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }

    @Override
    public Food getFoodById(Integer id) {
        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food", "foodId", String.valueOf(id)));
        return food;
    }

    @Override
    public Food createFood(Food food) {
        return foodRepository.save(food);
    }

    @Override
    public Food updateFood(Integer id, Food updateFood) {
        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food", "foodId", String.valueOf(id)));

        food.setName(updateFood.getName());
        food.setDescription(updateFood.getDescription());
        food.setPrice(updateFood.getPrice());
        food.setQuantity(updateFood.getQuantity());
        food.setFoodType(updateFood.getFoodType());

        return foodRepository.save(food);
    }


    @Override
    public ApiResponse deleteFoodById(Integer id) {
        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food", "foodId", String.valueOf(id)));

        foodRepository.delete(food);
        String message = "Food with foodId: " + id + " deleted successfully !!!";

        return new ApiResponse(message, false);
    }
}
