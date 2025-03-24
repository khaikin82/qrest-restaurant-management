package com.khaikin.qrest.foodtype;

import com.khaikin.qrest.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodTypeServiceImpl implements FoodTypeService {
    private final FoodTypeRepository foodTypeRepository;

    @Override
    public List<FoodType> getAllFoodTypes() {
        return foodTypeRepository.findAll();
    }

    @Override
    public FoodType getFoodTypeById(Integer id) {
        return foodTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FoodType not found with id: " + id));
    }

    @Override
    public FoodType createFoodType(FoodType foodType) {
        return foodTypeRepository.save(foodType);
    }

    @Override
    public FoodType updateFoodType(Integer id, FoodType foodType) {
        FoodType existingFoodType = getFoodTypeById(id);
        existingFoodType.setName(foodType.getName());
        existingFoodType.setDescription(foodType.getDescription());
        return foodTypeRepository.save(existingFoodType);
    }

    @Override
    public void deleteFoodType(Integer id) {
        foodTypeRepository.deleteById(id);
    }
}