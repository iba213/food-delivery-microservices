package com.champsoft.fooddelivery.orders.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record UpdateOrderRequest(
        @NotBlank String street,
        @NotBlank String city,
        @NotBlank String province,
        @NotBlank String postalCode,
        @NotBlank String paymentMethod,
        @NotBlank String paymentLastFourDigits,
        @NotEmpty List<@Valid OrderItemRequest> items
) {
}
