package com.khaikin.qrest.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khaikin.qrest.combo.Combo;
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
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    @Column(columnDefinition = "TEXT")
    private String imageUrl;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private List<Food> foods = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private List<Combo> combos = new ArrayList<>();

    public Category(String name, String description, String imageUrl) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
    }
}
