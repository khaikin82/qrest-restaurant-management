package com.khaikin.qrest.foodorder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khaikin.qrest.food.Food;
import com.khaikin.qrest.order.Order;
import com.khaikin.qrest.order.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FoodOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;
    @Column(precision = 15, scale = 2, nullable = false)
    private BigDecimal price;

    private boolean isCompleted;

    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food food;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;


}
