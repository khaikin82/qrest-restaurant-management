package com.khaikin.qrest.staff;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khaikin.qrest.user.User;
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
    private String imageUrl;

    @Column(precision = 15, scale = 2)
    private BigDecimal salary;

    @JsonIgnore
    @OneToOne(mappedBy = "staff")
    private User user;

    @Enumerated(EnumType.STRING)
    private Position position;  // WAITER, CASHIER, CHEF
}
