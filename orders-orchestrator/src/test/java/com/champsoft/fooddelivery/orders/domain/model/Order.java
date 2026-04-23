package com.champsoft.fooddelivery.orders.domain.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order {

    private final UUID id;
    private final UUID customerId;
    private final UUID restaurantId;
    private UUID driverId;
    private OrderStatus status;
    private DeliveryAddress deliveryAddress;
    private PaymentInfo paymentInfo;
    private final List<OrderItem> items = new ArrayList<>();
    private BigDecimal totalPrice;

    public Order(UUID id, UUID customerId, UUID restaurantId, UUID driverId, OrderStatus status,
                 DeliveryAddress deliveryAddress, PaymentInfo paymentInfo, List<OrderItem> items, BigDecimal totalPrice) {
        if (id == null || customerId == null || restaurantId == null) {
            throw new IllegalArgumentException("Order, customer, and restaurant ids are required");
        }
        this.id = id;
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        this.driverId = driverId;
        this.status = status == null ? OrderStatus.CREATED : status;
        this.deliveryAddress = deliveryAddress;
        this.paymentInfo = paymentInfo;
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item");
        }
        this.items.addAll(items);
        this.totalPrice = totalPrice == null ? calculateTotal() : totalPrice;
    }

    public void replaceItems(List<OrderItem> newItems) {
        if (newItems == null || newItems.isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item");
        }
        ensureMutable();
        items.clear();
        items.addAll(newItems);
        totalPrice = calculateTotal();
    }

    public void updateDetails(DeliveryAddress deliveryAddress, PaymentInfo paymentInfo, List<OrderItem> newItems) {
        ensureMutable();
        this.deliveryAddress = deliveryAddress;
        this.paymentInfo = paymentInfo;
        replaceItems(newItems);
    }

    public void assignDriver(UUID driverId) {
        ensureMutable();
        if (driverId == null) {
            throw new IllegalArgumentException("Driver id is required");
        }
        this.driverId = driverId;
        this.status = OrderStatus.ASSIGNED;
    }

    public void markDelivered() {
        if (status != OrderStatus.ASSIGNED) {
            throw new IllegalStateException("Only assigned orders can be delivered");
        }
        this.status = OrderStatus.DELIVERED;
    }

    public void cancel() {
        ensureMutable();
        this.status = OrderStatus.CANCELLED;
    }

    private void ensureMutable() {
        if (status == OrderStatus.DELIVERED || status == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Completed or cancelled orders cannot be modified");
        }
    }

    private BigDecimal calculateTotal() {
        return items.stream().map(OrderItem::lineTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public UUID getId() {
        return id;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public UUID getDriverId() {
        return driverId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public PaymentInfo getPaymentInfo() {
        return paymentInfo;
    }

    public List<OrderItem> getItems() {
        return List.copyOf(items);
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
}
