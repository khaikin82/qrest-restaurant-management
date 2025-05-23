package com.khaikin.qrest.table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khaikin.qrest.tableorder.TableOrder;
import com.khaikin.qrest.tablereservation.TableReservation;
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
public class RestaurantTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name; // có thể là số bàn hoặc id bàn (bàn 1, bàn D2,..)
    private int capacity;

    @Enumerated(EnumType.STRING)
    private RestaurantTableStatus status = RestaurantTableStatus.AVAILABLE;

    @JsonIgnore
    @OneToMany(mappedBy = "restaurantTable")
    private List<TableReservation> tableReservations = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "restaurantTable")
    private List<TableOrder> tableOrders = new ArrayList<>();

    public RestaurantTable(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    public RestaurantTable(String name, int capacity, RestaurantTableStatus status) {
        this.name = name;
        this.capacity = capacity;
        this.status = status;
    }
}
