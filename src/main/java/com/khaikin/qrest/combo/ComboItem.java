package com.khaikin.qrest.combo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ComboItem(
        @NotNull
        Long id,
        @NotNull
        @Min(value = 1)
        Integer quantity) {
}
