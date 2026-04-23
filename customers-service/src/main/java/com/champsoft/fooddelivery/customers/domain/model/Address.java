package com.champsoft.fooddelivery.customers.domain.model;

public record Address(String street, String city, String province, String postalCode) {

    public Address {
        if (isBlank(street) || isBlank(city) || isBlank(province) || isBlank(postalCode)) {
            throw new IllegalArgumentException("Customer address must be complete");
        }
    }

    private static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
