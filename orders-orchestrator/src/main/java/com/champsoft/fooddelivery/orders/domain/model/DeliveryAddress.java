package com.champsoft.fooddelivery.orders.domain.model;

public record DeliveryAddress(String street, String city, String province, String postalCode) {

    public DeliveryAddress {
        if (isBlank(street) || isBlank(city) || isBlank(province) || isBlank(postalCode)) {
            throw new IllegalArgumentException("Delivery address must be complete");
        }
    }

    private static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
