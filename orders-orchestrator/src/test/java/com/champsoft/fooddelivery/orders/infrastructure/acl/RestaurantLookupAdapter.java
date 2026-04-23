package com.champsoft.fooddelivery.orders.infrastructure.acl;

import com.champsoft.fooddeliveryplatform.modules.orders.application.port.out.RestaurantLookupPort;
import com.champsoft.fooddeliveryplatform.modules.restaurants.application.service.RestaurantCrudService;
import com.champsoft.fooddeliveryplatform.modules.restaurants.domain.model.Restaurant;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class RestaurantLookupAdapter implements RestaurantLookupPort {

    private final RestaurantCrudService restaurantCrudService;

    public RestaurantLookupAdapter(RestaurantCrudService restaurantCrudService) {
        this.restaurantCrudService = restaurantCrudService;
    }

    @Override
    public RestaurantSummary requireOrderableRestaurant(UUID restaurantId, List<UUID> menuItemIds) {
        Restaurant restaurant = restaurantCrudService.requireOrderable(restaurantId, menuItemIds);
        return new RestaurantSummary(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getMenuItems().stream()
                        .map(item -> new MenuItemSummary(item.getId(), item.getName(), item.getPrice().value()))
                        .toList()
        );
    }
}
