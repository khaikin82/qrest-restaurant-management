package com.khaikin.qrest.reservation;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReservationRequest {
    private LocalDateTime arrivalTime;
    private Integer numberOfGuests;
    private boolean isConfirmed; // cọc tiền xong sẽ là true
    private Double deposit;
    private String customerName;
    private String customerPhone;
    private List<Long> restaurantTableIds;
}
