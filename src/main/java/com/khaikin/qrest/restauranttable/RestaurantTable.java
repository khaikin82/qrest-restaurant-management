package com.khaikin.qrest.restauranttable;

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

    private String name; // có thể là số bàn hoặc id bàn (bàn 1, bàn D2,..)
    private int capacity;
    private boolean isAvailable;

    @OneToMany(mappedBy = "restaurantTable")
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "restaurantTable")
    private List<Order> orders = new ArrayList<>();

}
