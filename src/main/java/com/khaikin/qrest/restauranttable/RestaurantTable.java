package com.khaikin.qrest.restauranttable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khaikin.qrest.order.Order;
import com.khaikin.qrest.reservation.Reservation;
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
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name; // có thể là số bàn hoặc id bàn (bàn 1, bàn D2,..)
    private int capacity;
    private boolean isAvailable = true;

    @JsonIgnore
    @OneToMany(mappedBy = "restaurantTable")
    private List<Reservation> reservations = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "restaurantTable")
    private List<Order> orders = new ArrayList<>();

    public RestaurantTable(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    public RestaurantTable(String name, int capacity, boolean isAvailable) {
        this.name = name;
        this.capacity = capacity;
        this.isAvailable = isAvailable;
    }
}
