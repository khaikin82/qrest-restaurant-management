package com.khaikin.qrest.order;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private String note;
    private List<OrderItem> foodOrderItems;
    private List<OrderItem> comboOrderItems;
    private List<Long> restaurantTableIds;
    private Long reservationId;
}
