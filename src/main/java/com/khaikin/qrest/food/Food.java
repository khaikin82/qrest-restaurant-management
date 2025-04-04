package com.khaikin.qrest.food;

import com.khaikin.qrest.category.Category;
import com.khaikin.qrest.combofood.ComboFood;
import com.khaikin.qrest.foodorder.FoodOrder;
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

    @OneToMany(mappedBy = "food")
    private List<ComboFood> comboFoods = new ArrayList<>();

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
