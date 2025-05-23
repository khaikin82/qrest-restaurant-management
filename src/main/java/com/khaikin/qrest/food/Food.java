package com.khaikin.qrest.food;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khaikin.qrest.category.Category;
import com.khaikin.qrest.combofood.ComboFood;
import com.khaikin.qrest.foodorder.FoodOrder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    @Column(precision = 15, scale = 2)
    private BigDecimal price;
    private Integer quantity;
    @Column(columnDefinition = "TEXT")
    private String imageUrl;

    private String imageName;
    private String imageType;
    private String imagePath; // Đường dẫn tới ảnh lưu trong thư mục

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @JsonIgnore
    @OneToMany(mappedBy = "food")
    private List<ComboFood> comboFoods = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "food")
    private List<FoodOrder> foodOrders = new ArrayList<>();
}
