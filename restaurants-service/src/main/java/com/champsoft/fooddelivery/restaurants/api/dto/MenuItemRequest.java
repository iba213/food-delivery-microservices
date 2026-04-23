package com.champsoft.fooddelivery.restaurants.api.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record MenuItemRequest(
        UUID id,
        @NotBlank String name,
        @NotNull @DecimalMin("0.00") BigDecimal price,
        boolean available
) {
}
