package com.champsoft.fooddelivery.orders.application.port.out;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface RestaurantLookupPort {

    RestaurantSummary requireOrderableRestaurant(UUID restaurantId, List<UUID> menuItemIds);

    record RestaurantSummary(UUID id, String name, List<MenuItemSummary> menuItems) {
    }

    record MenuItemSummary(UUID id, String name, BigDecimal price) {
    }
}
