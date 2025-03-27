package com.khaikin.qrest.table;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Table {
    @Id
    @GeneratedValue
    private Long id;

    private String name; // có thể là số bàn hoặc id bàn (bàn 1, bàn D2,..)
    private Integer floor = 1; // ở tầng mấy?
    private int capacity;
    private boolean isAvailable;
}
