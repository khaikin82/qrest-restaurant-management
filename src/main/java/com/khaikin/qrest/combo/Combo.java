package com.khaikin.qrest.combo;

import com.khaikin.qrest.combofood.ComboFood;
import com.khaikin.qrest.comboorder.ComboOrder;
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
public class Combo {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;
    private Double price;
    private String imageUrl;

    @OneToMany(mappedBy = "combo")
    private List<ComboFood> comboFoods = new ArrayList<>();

    @OneToMany(mappedBy = "combo")
    private List<ComboOrder> comboOrders = new ArrayList<>();
}
