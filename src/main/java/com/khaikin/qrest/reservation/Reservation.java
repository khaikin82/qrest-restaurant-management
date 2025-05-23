package com.khaikin.qrest.reservation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khaikin.qrest.order.Order;
import com.khaikin.qrest.tablereservation.TableReservation;
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
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime bookingTime;
    private LocalDateTime arrivalTime;

    private Integer numberOfGuests;
    private boolean isConfirmed; // cọc tiền xong sẽ là true
    @Column(precision = 15, scale = 2)
    private BigDecimal deposit;

    private String customerName;
    private String customerPhone;

    @OneToMany(mappedBy = "reservation")
    private List<TableReservation> tableReservations = new ArrayList<>();

    @JsonIgnore
    @OneToOne(mappedBy = "reservation")
    private Order order;
}

