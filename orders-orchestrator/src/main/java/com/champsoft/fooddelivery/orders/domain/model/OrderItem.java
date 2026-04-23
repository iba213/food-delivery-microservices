package com.champsoft.fooddelivery.orders.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderItem {

    private final UUID id;
    private final UUID menuItemId;
    private int quantity;
    private BigDecimal price;

    public OrderItem(UUID id, UUID menuItemId, int quantity, BigDecimal price) {
        if (id == null || menuItemId == null) {
            throw new IllegalArgumentException("Order item ids are required");
        }
        this.id = id;
        this.menuItemId = menuItemId;
        update(quantity, price);
    }

    public void update(int quantity, BigDecimal price) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Order item quantity must be greater than zero");
        }
        if (price == null || price.signum() < 0) {
            throw new IllegalArgumentException("Order item price must be zero or positive");
        }
        this.quantity = quantity;
        this.price = price;
    }

    public BigDecimal lineTotal() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    public UUID getId() {
        return id;
    }

    public UUID getMenuItemId() {
        return menuItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
