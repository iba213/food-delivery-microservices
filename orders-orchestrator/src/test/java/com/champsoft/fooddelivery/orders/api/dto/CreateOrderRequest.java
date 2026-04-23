package com.champsoft.fooddelivery.orders.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record CreateOrderRequest(
        @NotNull UUID customerId,
        @NotNull UUID restaurantId,
        @NotBlank String street,
        @NotBlank String city,
        @NotBlank String province,
        @NotBlank String postalCode,
        @NotBlank String paymentMethod,
        @NotBlank String paymentLastFourDigits,
        @NotEmpty List<@Valid OrderItemRequest> items
) {
}
