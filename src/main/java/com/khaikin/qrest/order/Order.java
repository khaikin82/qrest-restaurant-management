package com.khaikin.qrest.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khaikin.qrest.comboorder.ComboOrder;
import com.khaikin.qrest.foodorder.FoodOrder;
import com.khaikin.qrest.reservation.Reservation;
import com.khaikin.qrest.restauranttable.RestaurantTable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal totalPrice;
    private String note;

    @Enumerated(EnumType.STRING)
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
