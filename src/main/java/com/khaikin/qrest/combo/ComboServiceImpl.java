package com.khaikin.qrest.combo;

import com.khaikin.qrest.combofood.ComboFood;
import com.khaikin.qrest.combofood.ComboFoodRepository;
import com.khaikin.qrest.exception.ResourceNotFoundException;
import com.khaikin.qrest.food.Food;
import com.khaikin.qrest.food.FoodRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComboServiceImpl implements ComboService {
    private final ComboRepository comboRepository;
    private final FoodRepository foodRepository;
    private final ComboFoodRepository comboFoodRepository;

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
    public void deleteComboById(Long id) {
        Combo existingCombo = comboRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Combo", "comboId", id));
        comboRepository.delete(existingCombo);
    }
}