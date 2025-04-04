package com.khaikin.qrest.customer;

import com.khaikin.qrest.order.Order;
import com.khaikin.qrest.reservation.Reservation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer {
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private CustomerTitle customerTitle;

    private String firstname;
    private String lastname;
    private String phone;

    @OneToMany(mappedBy = "customer")
    private List<Reservation> reservations = new ArrayList<>();

}
