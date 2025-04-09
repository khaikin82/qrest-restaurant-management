package com.khaikin.qrest.combo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ComboRequest {
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private List<ComboItem> comboItems;
}
