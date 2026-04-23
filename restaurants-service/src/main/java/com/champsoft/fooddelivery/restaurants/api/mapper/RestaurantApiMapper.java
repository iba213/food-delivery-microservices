package com.champsoft.fooddelivery.restaurants.api.mapper;

import com.champsoft.fooddelivery.restaurants.api.dto.MenuItemResponse;
import com.champsoft.fooddelivery.restaurants.api.dto.RestaurantResponse;
import com.champsoft.fooddelivery.restaurants.domain.model.MenuItem;
import com.champsoft.fooddelivery.restaurants.domain.model.Restaurant;
import org.springframework.stereotype.Component;

@Component
public class RestaurantApiMapper {

    public RestaurantResponse toResponse(Restaurant restaurant) {
        return new RestaurantResponse(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getAddress().street(),
                restaurant.getAddress().city(),
                restaurant.getAddress().province(),
                restaurant.getAddress().postalCode(),
                restaurant.isOpen(),
                restaurant.getMenuItems().stream().map(this::toMenuItemResponse).toList()
        );
    }

    private MenuItemResponse toMenuItemResponse(MenuItem item) {
        return new MenuItemResponse(item.getId(), item.getName(), item.getPrice().value(), item.isAvailable());
    }
}
