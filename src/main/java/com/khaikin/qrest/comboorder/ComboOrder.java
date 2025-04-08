package com.khaikin.qrest.comboorder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khaikin.qrest.combo.Combo;
import com.khaikin.qrest.order.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ComboOrder {
    @Id
    @GeneratedValue
    private Long id;

    private Integer quantity;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "combo_id")
    private Combo combo;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
