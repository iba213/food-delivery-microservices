package com.champsoft.fooddelivery.restaurants.api.dto;

import java.util.List;
import java.util.UUID;

public record RestaurantResponse(
        UUID id,
        String name,
        String street,
        String city,
        String province,
        String postalCode,
        boolean open,
        List<MenuItemResponse> menuItems
) {
}
