package com.khaikin.qrest.staff;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffDTO {
    private Long id;
    private String fullName;
    private BigDecimal salary;
    private Position position;
}