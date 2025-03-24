package com.khaikin.qrest.foodtype;

import java.util.List;

public interface FoodTypeService {
    List<FoodType> getAllFoodTypes();
    FoodType getFoodTypeById(Integer id);
    FoodType createFoodType(FoodType foodType);
    FoodType updateFoodType(Integer id, FoodType foodType);
    void deleteFoodType(Integer id);
}
