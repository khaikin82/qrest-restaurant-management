package com.khaikin.qrest.combo;

import com.khaikin.qrest.category.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class ComboRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private List<ComboItem> comboItems;
    private Long categoryId;
}
