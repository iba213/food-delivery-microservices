package com.champsoft.fooddelivery.restaurants.domain.model;

import java.util.UUID;

public class MenuItem {

    private final UUID id;
    private String name;
    private Price price;
    private boolean available;

    public MenuItem(UUID id, String name, Price price, boolean available) {
        if (id == null) {
            throw new IllegalArgumentException("Menu item id is required");
        }
        this.id = id;
        update(name, price, available);
    }

    public void update(String name, Price price, boolean available) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Menu item name is required");
        }
        this.name = name;
        this.price = price;
        this.available = available;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }
}
