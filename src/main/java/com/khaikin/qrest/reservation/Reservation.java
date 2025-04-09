package com.khaikin.qrest.reservation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khaikin.qrest.order.Order;
import com.khaikin.qrest.restauranttable.RestaurantTable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reservation {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime bookingTime;
    private LocalDateTime arrivalTime;

    private Integer numberOfGuests;
    private boolean isConfirmed; // cọc tiền xong sẽ là true
    private Double deposit;

    private String customerName;
    private String customerPhone;

    @ManyToOne
    @JoinColumn(name = "restaurant_table_id")
    private RestaurantTable restaurantTable;

    @JsonIgnore
    @OneToOne(mappedBy = "reservation")
    private Order order;
}

