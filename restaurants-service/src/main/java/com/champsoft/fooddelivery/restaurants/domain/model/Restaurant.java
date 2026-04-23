package com.champsoft.fooddelivery.restaurants.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Restaurant {

    private final UUID id;
    private String name;
    private RestaurantAddress address;
    private boolean open;
    private final List<MenuItem> menuItems = new ArrayList<>();

    public Restaurant(UUID id, String name, RestaurantAddress address, boolean open, List<MenuItem> menuItems) {
        if (id == null) {
            throw new IllegalArgumentException("Restaurant id is required");
        }
        this.id = id;
        update(name, address, open, menuItems);
    }

    public void update(String name, RestaurantAddress address, boolean open, List<MenuItem> menuItems) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Restaurant name is required");
        }
        if (menuItems == null || menuItems.isEmpty()) {
            throw new IllegalArgumentException("Restaurant must have at least one menu item");
        }
        this.name = name;
        this.address = address;
        this.open = open;
        this.menuItems.clear();
        this.menuItems.addAll(menuItems);
    }

    public MenuItem requireAvailableMenuItem(UUID menuItemId) {
        if (!open) {
            throw new IllegalStateException("Restaurant is closed");
        }
        return menuItems.stream()
                .filter(item -> item.getId().equals(menuItemId))
                .findFirst()
                .filter(MenuItem::isAvailable)
                .orElseThrow(() -> new IllegalStateException("Menu item is unavailable: " + menuItemId));
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public RestaurantAddress getAddress() {
        return address;
    }

    public boolean isOpen() {
        return open;
    }

    public List<MenuItem> getMenuItems() {
        return List.copyOf(menuItems);
    }
}
