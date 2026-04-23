package com.champsoft.fooddelivery.restaurants.domain.model;

public record RestaurantAddress(String street, String city, String province, String postalCode) {

    public RestaurantAddress {
        if (isBlank(street) || isBlank(city) || isBlank(province) || isBlank(postalCode)) {
            throw new IllegalArgumentException("Restaurant address must be complete");
        }
    }

    private static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
