package com.khaikin.qrest.order;

import com.khaikin.qrest.reservation.Reservation;
import com.khaikin.qrest.restauranttable.RestaurantTable;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class OrderRequest {
    private String note;
    private List<OrderItem> foodOrderItems;
    private List<OrderItem> comboOrderItems;
    private RestaurantTable restaurantTable;
    private Reservation reservation;
}
