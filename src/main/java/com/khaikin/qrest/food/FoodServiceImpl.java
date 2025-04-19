package com.khaikin.qrest.food;

import com.khaikin.qrest.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepository;
    private final String uploadDir = "uploads/images/food";

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
    public Food createFood(Food food, MultipartFile imageFile)
            throws IOException {
        String imageName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
        String imageType = imageFile.getContentType();

        Path path = Paths.get(uploadDir + imageName);
        Files.createDirectories(path.getParent());  // Tạo thư mục nếu chưa tồn tại
        imageFile.transferTo(path.toFile());

        food.setImageName(imageName);
        food.setImageType(imageType);
        food.setImagePath(path.toString());
        return foodRepository.save(food);
    }

    @Override
    public Food updateFood(Long id, Food food, MultipartFile updateImageFile)
            throws IOException {
        Food existingFood = foodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food", "foodId", id));

        existingFood.setName(food.getName());
        existingFood.setDescription(food.getDescription());
        existingFood.setPrice(food.getPrice());
        existingFood.setQuantity(food.getQuantity());
        existingFood.setCategory(food.getCategory());
        existingFood.setImageUrl(food.getImageUrl());

        String imageName = System.currentTimeMillis() + "_" + updateImageFile.getOriginalFilename();
        String imageType = updateImageFile.getContentType();
        Path path = Paths.get(Paths.get(uploadDir).toAbsolutePath() + "/" + imageName);
        Files.createDirectories(path.getParent());  // Tạo thư mục nếu chưa tồn tại
        updateImageFile.transferTo(path.toFile());

        existingFood.setImageName(imageName);
        existingFood.setImageType(imageType);
        existingFood.setImagePath(path.toString());

        return foodRepository.save(existingFood);
    }


    @Override
    public void deleteFoodById(Long id) {
        Food existingFood = foodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food", "foodId", id));

        foodRepository.delete(existingFood);
    }
}
