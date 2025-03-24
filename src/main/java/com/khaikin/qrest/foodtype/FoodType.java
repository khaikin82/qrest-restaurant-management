package com.khaikin.qrest.foodtype;

import com.khaikin.qrest.food.Food;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FoodType {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;
    private String description;

    @OneToMany(mappedBy = "foodType")
    private List<Food> foods = new ArrayList<>();
}
