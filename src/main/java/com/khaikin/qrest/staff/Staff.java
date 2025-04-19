package com.khaikin.qrest.staff;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private LocalDate dob;

    private String phoneNumber;
    private String address;

    @Column(precision = 15, scale = 2)
    private BigDecimal salary;

    @Enumerated(EnumType.STRING)
    private Position position;  // WAITER, CASHIER, CHEF
}
