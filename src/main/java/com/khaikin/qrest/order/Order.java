package com.khaikin.qrest.order;

import com.khaikin.qrest.comboorder.ComboOrder;
import com.khaikin.qrest.foodorder.FoodOrder;
import com.khaikin.qrest.reservation.Reservation;
import com.khaikin.qrest.restauranttable.RestaurantTable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "restaurant_order")
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    private Double price;
    private String note;

    private OrderStatus orderStatus;
    private LocalDateTime orderTime;

    @OneToMany(mappedBy = "order")
    private List<FoodOrder> foodOrders = new ArrayList<>();

    @OneToMany(mappedBy = "order")
    private List<ComboOrder> comboOrders = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "restaurant_table_id")
    private RestaurantTable restaurantTable;

    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;
}
