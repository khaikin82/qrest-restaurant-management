package com.khaikin.qrest.combo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khaikin.qrest.comboorder.ComboOrder;
import com.khaikin.qrest.food.Food;
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
public class Combo {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;
    private Double price;
    private String imageUrl;

    @ManyToMany
    @JoinTable(
            name = "combo_food",
            joinColumns = @JoinColumn(name = "combo_id"),
            inverseJoinColumns = @JoinColumn(name = "food_id")
    )
    private Set<Food> foods = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "combo")
    private List<ComboOrder> comboOrders = new ArrayList<>();
}
