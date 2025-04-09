package com.khaikin.qrest.combofood;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khaikin.qrest.combo.Combo;
import com.khaikin.qrest.food.Food;
import com.khaikin.qrest.order.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ComboFood {
    @Id
    @GeneratedValue
    private Long id;

    private Integer quantity;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "combo_id")
    private Combo combo;

    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food food;
}
