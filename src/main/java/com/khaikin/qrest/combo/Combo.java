package com.khaikin.qrest.combo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khaikin.qrest.combofood.ComboFood;
import com.khaikin.qrest.comboorder.ComboOrder;
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
public class Combo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    @Column(precision = 15, scale = 2)
    private BigDecimal price;
    private String imageUrl;

    @OneToMany(mappedBy = "combo")
    private List<ComboFood> comboFoods = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "combo")
    private List<ComboOrder> comboOrders = new ArrayList<>();
}
