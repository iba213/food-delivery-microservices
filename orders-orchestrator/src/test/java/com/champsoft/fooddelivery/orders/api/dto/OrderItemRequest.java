package com.champsoft.fooddelivery.orders.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record OrderItemRequest(
        UUID id,
        @NotNull UUID menuItemId,
        @Min(1) int quantity
) {
}
