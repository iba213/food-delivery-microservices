package com.champsoft.fooddelivery.orders.api.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemResponse(
        UUID id,
        UUID menuItemId,
        int quantity,
        BigDecimal price
) {
}
