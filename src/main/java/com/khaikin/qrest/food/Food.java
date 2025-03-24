package com.khaikin.qrest.food;

import com.khaikin.qrest.combo.Combo;
import com.khaikin.qrest.foodtype.FoodType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Food {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;
    private String description;
    private Double price;
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "food_type_id")
    private FoodType foodType;

    @ManyToOne
    @JoinColumn(name = "combo_id")
    private Combo combo;
}
