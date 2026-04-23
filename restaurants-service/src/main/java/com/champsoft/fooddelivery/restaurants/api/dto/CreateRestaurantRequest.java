package com.champsoft.fooddelivery.restaurants.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CreateRestaurantRequest(
        @NotBlank String name,
        @NotBlank String street,
        @NotBlank String city,
        @NotBlank String province,
        @NotBlank String postalCode,
        boolean open,
        @NotEmpty List<@Valid MenuItemRequest> menuItems
) {
}
