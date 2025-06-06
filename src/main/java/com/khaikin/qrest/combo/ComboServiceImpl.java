package com.khaikin.qrest.combo;

import com.khaikin.qrest.category.Category;
import com.khaikin.qrest.category.CategoryRepository;
import com.khaikin.qrest.combofood.ComboFood;
import com.khaikin.qrest.combofood.ComboFoodRepository;
import com.khaikin.qrest.exception.ResourceNotFoundException;
import com.khaikin.qrest.food.Food;
import com.khaikin.qrest.food.FoodRepository;
import com.khaikin.qrest.util.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComboServiceImpl implements ComboService {
    private final ComboRepository comboRepository;
    private final FoodRepository foodRepository;
    private final ComboFoodRepository comboFoodRepository;
    private final CategoryRepository categoryRepository;
    private final FileStorageService fileStorageService;
    private final String uploadDir = "images/combo";

    @Override
    public List<Combo> getAllCombos() {
        return comboRepository.findAll();
    }

    @Override
    public Combo getComboById(Long id) {
        return comboRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Combo", "comboId", id));
    }

    @Override
    @Transactional
    public Combo createCombo(ComboRequest comboRequest) {
        Combo combo = new Combo();
        combo.setName(comboRequest.getName());
        combo.setDescription(comboRequest.getDescription());
        combo.setPrice(comboRequest.getPrice());
        combo.setImageUrl(comboRequest.getImageUrl());

        List<ComboFood> comboFoods = new ArrayList<>();
        for (ComboItem comboItem : comboRequest.getComboItems()) {
            Food food = foodRepository.findById(comboItem.id())
                    .orElseThrow(() -> new ResourceNotFoundException("Food", "foodId", comboItem.id()));
            Integer quantity = comboItem.quantity();

            ComboFood comboFood = new ComboFood();
            comboFood.setFood(food);
            comboFood.setQuantity(quantity);
            comboFood.setCombo(combo);

            comboFoods.add(comboFood);
        }
        comboFoodRepository.saveAll(comboFoods);
        combo.setComboFoods(comboFoods);

        Long categoryId = comboRequest.getCategoryId();
        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
            combo.setCategory(category);
        }

        return comboRepository.save(combo);
    }

    @Override
    public Combo updateCombo(Long id, Combo combo) {
        Combo existingCombo = comboRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Combo", "comboId", id));

        existingCombo.setName(combo.getName());
        existingCombo.setDescription(combo.getDescription());
        existingCombo.setPrice(combo.getPrice());
        return comboRepository.save(existingCombo);
    }

    @Override
    public Combo createCombo(ComboRequest comboRequest, MultipartFile imageFile, HttpServletRequest request)
            throws IOException {
        Combo combo = new Combo();
        combo.setName(comboRequest.getName());
        combo.setDescription(comboRequest.getDescription());
        combo.setPrice(comboRequest.getPrice());
        combo.setImageUrl(comboRequest.getImageUrl());

        List<ComboFood> comboFoods = new ArrayList<>();
        for (ComboItem comboItem : comboRequest.getComboItems()) {
            Food food = foodRepository.findById(comboItem.id())
                    .orElseThrow(() -> new ResourceNotFoundException("Food", "foodId", comboItem.id()));
            Integer quantity = comboItem.quantity();

            ComboFood comboFood = new ComboFood();
            comboFood.setFood(food);
            comboFood.setQuantity(quantity);
            comboFood.setCombo(combo);

            comboFoods.add(comboFood);
        }
        comboFoodRepository.saveAll(comboFoods);
        combo.setComboFoods(comboFoods);

        String imageUrl = fileStorageService.storeFile(imageFile, uploadDir, request);
        combo.setImageUrl(imageUrl);

        Long categoryId = comboRequest.getCategoryId();
        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
            combo.setCategory(category);
        }

        return comboRepository.save(combo);
    }

    @Override
    public Combo updateCombo(Long id, Combo combo, MultipartFile updateImageFile, HttpServletRequest request)
            throws IOException {
        Combo existingCombo = comboRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Combo", "comboId", id));

        existingCombo.setName(combo.getName());
        existingCombo.setDescription(combo.getDescription());
        existingCombo.setPrice(combo.getPrice());

        String imageUrl = fileStorageService.storeFile(updateImageFile, uploadDir, request);
        existingCombo.setImageUrl(imageUrl);

        return comboRepository.save(existingCombo);
    }

    @Override
    public void deleteComboById(Long id) {
        Combo existingCombo = comboRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Combo", "comboId", id));
        comboRepository.delete(existingCombo);
    }
}