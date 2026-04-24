package com.champsoft.fooddelivery.orders.application.service;

import com.champsoft.fooddelivery.orders.application.port.out.*;
import com.champsoft.fooddelivery.orders.domain.model.DeliveryAddress;
import com.champsoft.fooddelivery.orders.domain.model.Order;
import com.champsoft.fooddelivery.orders.domain.model.OrderItem;
import com.champsoft.fooddelivery.orders.domain.model.PaymentInfo;
import com.champsoft.fooddelivery.orders.api.dto.CreateOrderRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderOrchestratorService {
    private final CustomerLookupPort customerLookup;
    private final RestaurantLookupPort restaurantLookup;
    private final OrderRepositoryPort orderRepository;

    public OrderOrchestratorService(CustomerLookupPort customerLookup,
                                    RestaurantLookupPort restaurantLookup,
                                    OrderRepositoryPort orderRepository) {
        this.customerLookup = customerLookup;
        this.restaurantLookup = restaurantLookup;
        this.orderRepository = orderRepository;
    }

    public Order processOrder(CreateOrderRequest request) {
        // 1. Validate Customer
        CustomerLookupPort.CustomerSummary customerSummary = customerLookup.requireCustomer(request.customerId());

        // 2. Validate Restaurant and Menu Items
        List<UUID> menuItemIds = request.items().stream()
                .map(item -> item.menuItemId())
                .collect(Collectors.toList());
        RestaurantLookupPort.RestaurantSummary restaurantSummary = restaurantLookup.requireOrderableRestaurant(request.restaurantId(), menuItemIds);

        // 3. Create and Save
        List<OrderItem> orderItems = request.items().stream().map(reqItem -> {
            RestaurantLookupPort.MenuItemSummary menuItem = restaurantSummary.menuItems().stream()
                    .filter(m -> m.id().equals(reqItem.menuItemId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Menu item not found in restaurant details"));
            return new OrderItem(UUID.randomUUID(), reqItem.menuItemId(), reqItem.quantity(), menuItem.price());
        }).toList();

        DeliveryAddress address = new DeliveryAddress(request.street(), request.city(), request.province(), request.postalCode());
        PaymentInfo paymentInfo = new PaymentInfo(request.paymentMethod(), request.paymentLastFourDigits());

        Order order = new Order(
                UUID.randomUUID(),
                request.customerId(),
                request.restaurantId(),
                null,
                null,
                address,
                paymentInfo,
                orderItems,
                null
        );

        return orderRepository.save(order);
    }
}
