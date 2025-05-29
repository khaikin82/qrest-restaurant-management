package com.khaikin.qrest.reservation;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReservationRequest {
    private LocalDateTime arrivalTime;
    private Integer numberOfGuests;
    private ReservationStatus reservationStatus; //  PENDING, CONFIRMED, COMPLETED, CANCELLED
    private Double deposit;
    private String customerName;
    private String customerPhone;
    private List<Long> restaurantTableIds;
}
