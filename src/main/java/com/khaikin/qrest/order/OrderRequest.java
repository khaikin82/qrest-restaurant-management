package com.khaikin.qrest.order;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderRequest {
    private Double price;
    private String note;
    List<Integer> foodIds;
}
