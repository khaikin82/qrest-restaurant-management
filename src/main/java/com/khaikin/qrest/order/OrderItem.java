package com.khaikin.qrest.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderItem(
        @NotNull
        Long id,
        @NotNull
        @Min(value = 1)
        Integer quantity) {
}
