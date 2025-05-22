package com.khaikin.qrest.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RevenueResponse {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Double totalRevenue;
    private String periodType; // DAILY, WEEKLY, MONTHLY, QUARTERLY, YEARLY
} 