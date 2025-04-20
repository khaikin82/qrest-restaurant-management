package com.khaikin.qrest.tablereservation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khaikin.qrest.order.Order;
import com.khaikin.qrest.reservation.Reservation;
import com.khaikin.qrest.table.RestaurantTable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "restaurant_table_reservation")
public class TableReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_table_id")
    private RestaurantTable restaurantTable;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;
}
