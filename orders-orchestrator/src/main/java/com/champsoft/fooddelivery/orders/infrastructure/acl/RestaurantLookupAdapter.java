package com.champsoft.fooddelivery.orders.infrastructure.acl;

import com.champsoft.fooddelivery.orders.application.port.out.RestaurantLookupPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Component
public class RestaurantLookupAdapter implements RestaurantLookupPort {

    private final RestTemplate restTemplate;
    private final String restaurantServiceUrl;

    public RestaurantLookupAdapter(RestTemplate restTemplate,
                                   @Value("${app.services.restaurant.url:http://localhost:8083}") String restaurantServiceUrl) {
        this.restTemplate = restTemplate;
        this.restaurantServiceUrl = restaurantServiceUrl;
    }

    @Override
    public RestaurantSummary requireOrderableRestaurant(UUID restaurantId, List<UUID> menuItemIds) {
        try {
            // In a real application, you might pass menuItemIds to the restaurant service to check if they are orderable
            ResponseEntity<RestaurantDto> response = restTemplate.getForEntity(
                    restaurantServiceUrl + "/api/restaurants/{id}",
                    RestaurantDto.class,
                    restaurantId
            );

            RestaurantDto dto = response.getBody();
            if (dto == null) {
                throw new RuntimeException("Restaurant not found: " + restaurantId);
            }

            // Verify requested menu items exist
            List<MenuItemSummary> menuItems = dto.menuItems().stream()
                    .filter(item -> menuItemIds.contains(item.id()))
                    .map(item -> new MenuItemSummary(item.id(), item.name(), item.price()))
                    .toList();
                    
            if (menuItems.size() != menuItemIds.size()) {
                throw new RuntimeException("Some menu items were not found in the restaurant");
            }

            return new RestaurantSummary(
                    dto.id(),
                    dto.name(),
                    menuItems
            );
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("Restaurant not found: " + restaurantId, e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to lookup restaurant: " + restaurantId, e);
        }
    }

    record RestaurantDto(UUID id, String name, List<MenuItemDto> menuItems) {}
    record MenuItemDto(UUID id, String name, BigDecimal price) {}
}
