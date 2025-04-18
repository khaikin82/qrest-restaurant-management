package com.khaikin.qrest.order;

import com.khaikin.qrest.reservation.Reservation;
import com.khaikin.qrest.restauranttable.RestaurantTable;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private String note;
    private List<OrderItem> foodOrderItems;
    private List<OrderItem> comboOrderItems;
    private Long restaurantTableId;
    private Long reservationId;
}
