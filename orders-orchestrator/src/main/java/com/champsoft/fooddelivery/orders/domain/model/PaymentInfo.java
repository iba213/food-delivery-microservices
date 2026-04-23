package com.champsoft.fooddelivery.orders.domain.model;

public record PaymentInfo(String method, String lastFourDigits) {

    public PaymentInfo {
        if (method == null || method.isBlank()) {
            throw new IllegalArgumentException("Payment method is required");
        }
        if (lastFourDigits == null || !lastFourDigits.matches("\\d{4}")) {
            throw new IllegalArgumentException("Payment last four digits must contain exactly 4 numbers");
        }
    }
}
