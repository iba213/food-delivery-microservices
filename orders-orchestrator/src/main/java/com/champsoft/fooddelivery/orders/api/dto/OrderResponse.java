package com.champsoft.fooddelivery.orders.api.dto;

import com.champsoft.fooddelivery.orders.domain.model.OrderStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrderResponse(
        UUID id,
        UUID customerId,
        String customerName,
        UUID restaurantId,
        String restaurantName,
        UUID driverId,
        String driverName,
        OrderStatus status,
        BigDecimal totalPrice,
        String street,
        String city,
        String province,
        String postalCode,
        String paymentMethod,
        String paymentLastFourDigits,
        List<OrderItemResponse> items
) {
}
