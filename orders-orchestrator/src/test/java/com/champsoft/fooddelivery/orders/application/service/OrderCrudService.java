package com.champsoft.fooddelivery.orders.application.service;

import com.champsoft.fooddelivery.orders.api.dto.OrderItemRequest;
import com.champsoft.fooddelivery.orders.application.exception.OrderConflictException;
import com.champsoft.fooddelivery.orders.application.exception.OrderNotFoundException;
import com.champsoft.fooddelivery.orders.application.port.out.CustomerLookupPort;
import com.champsoft.fooddelivery.orders.application.port.out.DriverAssignmentPort;
import com.champsoft.fooddelivery.orders.application.port.out.OrderRepositoryPort;
import com.champsoft.fooddelivery.orders.application.port.out.RestaurantLookupPort;
import com.champsoft.fooddelivery.orders.domain.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderCrudService {

    private final OrderRepositoryPort orderRepositoryPort;
    private final CustomerLookupPort customerLookupPort;
    private final RestaurantLookupPort restaurantLookupPort;
    private final DriverAssignmentPort driverAssignmentPort;

    public OrderCrudService(OrderRepositoryPort orderRepositoryPort,
                            CustomerLookupPort customerLookupPort,
                            RestaurantLookupPort restaurantLookupPort,
                            DriverAssignmentPort driverAssignmentPort) {
        this.orderRepositoryPort = orderRepositoryPort;
        this.customerLookupPort = customerLookupPort;
        this.restaurantLookupPort = restaurantLookupPort;
        this.driverAssignmentPort = driverAssignmentPort;
    }

    public Order create(UUID customerId, UUID restaurantId, String street, String city, String province, String postalCode,
                        String paymentMethod, String paymentLastFourDigits, List<OrderItemRequest> itemRequests) {
        customerLookupPort.requireCustomer(customerId);
        RestaurantLookupPort.RestaurantSummary restaurant = restaurantLookupPort.requireOrderableRestaurant(
                restaurantId,
                itemRequests.stream().map(OrderItemRequest::menuItemId).toList()
        );
        Order order = new Order(
                UUID.randomUUID(),
                customerId,
                restaurantId,
                null,
                OrderStatus.CREATED,
                new DeliveryAddress(street, city, province, postalCode),
                new PaymentInfo(paymentMethod, paymentLastFourDigits),
                toOrderItems(itemRequests, restaurant),
                null
        );
        DriverAssignmentPort.DriverSummary driver = driverAssignmentPort.assignAvailableDriver();
        order.assignDriver(driver.id());
        return orderRepositoryPort.save(order);
    }

    @Transactional(readOnly = true)
    public List<Order> getAll() {
        return orderRepositoryPort.findAll();
    }

    @Transactional(readOnly = true)
    public Order getById(UUID id) {
        return orderRepositoryPort.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found: " + id));
    }

    public Order update(UUID id, String street, String city, String province, String postalCode,
                        String paymentMethod, String paymentLastFourDigits, List<OrderItemRequest> itemRequests) {
        Order order = getById(id);
        RestaurantLookupPort.RestaurantSummary restaurant = restaurantLookupPort.requireOrderableRestaurant(
                order.getRestaurantId(),
                itemRequests.stream().map(OrderItemRequest::menuItemId).toList()
        );
        try {
            order.updateDetails(
                    new DeliveryAddress(street, city, province, postalCode),
                    new PaymentInfo(paymentMethod, paymentLastFourDigits),
                    toOrderItems(itemRequests, restaurant)
            );
        } catch (IllegalStateException exception) {
            throw new OrderConflictException(exception.getMessage());
        }
        return orderRepositoryPort.save(order);
    }

    public Order deliver(UUID id) {
        Order order = getById(id);
        try {
            order.markDelivered();
        } catch (IllegalStateException exception) {
            throw new OrderConflictException(exception.getMessage());
        }
        driverAssignmentPort.markAvailable(order.getDriverId());
        return orderRepositoryPort.save(order);
    }

    public Order cancel(UUID id) {
        Order order = getById(id);
        try {
            order.cancel();
        } catch (IllegalStateException exception) {
            throw new OrderConflictException(exception.getMessage());
        }
        if (order.getDriverId() != null) {
            driverAssignmentPort.markAvailable(order.getDriverId());
        }
        return orderRepositoryPort.save(order);
    }

    public void delete(UUID id) {
        Order order = getById(id);
        if (order.getDriverId() != null && order.getStatus() == OrderStatus.ASSIGNED) {
            driverAssignmentPort.markAvailable(order.getDriverId());
        }
        orderRepositoryPort.deleteById(id);
    }

    private List<OrderItem> toOrderItems(List<OrderItemRequest> itemRequests, RestaurantLookupPort.RestaurantSummary restaurant) {
        Map<UUID, RestaurantLookupPort.MenuItemSummary> menuItemMap = restaurant.menuItems().stream()
                .collect(Collectors.toMap(RestaurantLookupPort.MenuItemSummary::id, Function.identity()));

        return itemRequests.stream()
                .map(request -> {
                    RestaurantLookupPort.MenuItemSummary menuItem = menuItemMap.get(request.menuItemId());
                    if (menuItem == null) {
                        throw new OrderConflictException("Menu item does not belong to restaurant: " + request.menuItemId());
                    }
                    return new OrderItem(
                            request.id() == null ? UUID.randomUUID() : request.id(),
                            request.menuItemId(),
                            request.quantity(),
                            menuItem.price()
                    );
                })
                .toList();
    }
}
