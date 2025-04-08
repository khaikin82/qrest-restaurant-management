package com.khaikin.qrest.food;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khaikin.qrest.category.Category;
import com.khaikin.qrest.combo.Combo;
import com.khaikin.qrest.foodorder.FoodOrder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Food {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @JsonIgnore
    @ManyToMany(mappedBy = "foods")
    private Set<Combo> combos = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "food")
    private List<FoodOrder> foodOrders = new ArrayList<>();

    public Food(String name, String description, Double price, Integer quantity, String imageUrl, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    public Food(String name, String description, Double price, Integer quantity, String imageUrl) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
    }

    public Food(String name, String description, Double price, Integer quantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }
}
