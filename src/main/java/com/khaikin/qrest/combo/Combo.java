package com.khaikin.qrest.combo;

import com.khaikin.qrest.food.Food;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Combo {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;
    private String description;
    private Double price;

    @OneToMany(mappedBy = "combo")
    private List<Food> foods = new ArrayList<>();
}
