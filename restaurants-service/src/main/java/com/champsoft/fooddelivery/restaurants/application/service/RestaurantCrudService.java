package com.champsoft.fooddelivery.restaurants.application.service;

import com.champsoft.fooddelivery.restaurants.api.dto.MenuItemRequest;
import com.champsoft.fooddelivery.restaurants.application.exception.RestaurantConflictException;
import com.champsoft.fooddelivery.restaurants.application.exception.RestaurantNotFoundException;
import com.champsoft.fooddelivery.restaurants.application.port.out.RestaurantRepositoryPort;
import com.champsoft.fooddelivery.restaurants.domain.model.MenuItem;
import com.champsoft.fooddelivery.restaurants.domain.model.Price;
import com.champsoft.fooddelivery.restaurants.domain.model.Restaurant;
import com.champsoft.fooddelivery.restaurants.domain.model.RestaurantAddress;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class RestaurantCrudService {

    private final RestaurantRepositoryPort restaurantRepositoryPort;

    public RestaurantCrudService(RestaurantRepositoryPort restaurantRepositoryPort) {
        this.restaurantRepositoryPort = restaurantRepositoryPort;
    }

    public Restaurant create(String name, String street, String city, String province, String postalCode, boolean open, List<MenuItemRequest> menuItems) {
        return restaurantRepositoryPort.save(new Restaurant(
                UUID.randomUUID(),
                name,
                new RestaurantAddress(street, city, province, postalCode),
                open,
                toMenuItems(menuItems)
        ));
    }

    @Transactional(readOnly = true)
    public List<Restaurant> getAll() {
        return restaurantRepositoryPort.findAll();
    }

    @Transactional(readOnly = true)
    public Restaurant getById(UUID id) {
        return restaurantRepositoryPort.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found: " + id));
    }

    public Restaurant update(UUID id, String name, String street, String city, String province, String postalCode, boolean open, List<MenuItemRequest> menuItems) {
        Restaurant restaurant = getById(id);
        restaurant.update(name, new RestaurantAddress(street, city, province, postalCode), open, toMenuItems(menuItems));
        return restaurantRepositoryPort.save(restaurant);
    }

    public void delete(UUID id) {
        getById(id);
        restaurantRepositoryPort.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Restaurant requireOrderable(UUID restaurantId, List<UUID> menuItemIds) {
        Restaurant restaurant = getById(restaurantId);
        try {
            menuItemIds.forEach(restaurant::requireAvailableMenuItem);
        } catch (IllegalStateException exception) {
            throw new RestaurantConflictException(exception.getMessage());
        }
        return restaurant;
    }

    private List<MenuItem> toMenuItems(List<MenuItemRequest> menuItems) {
        return menuItems.stream()
                .map(item -> new MenuItem(
                        item.id() == null ? UUID.randomUUID() : item.id(),
                        item.name(),
                        new Price(item.price()),
                        item.available()
                ))
                .toList();
    }
}
