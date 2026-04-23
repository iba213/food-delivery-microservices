package com.champsoft.fooddelivery.orders.api.mapper;

import com.champsoft.fooddelivery.orders.api.dto.OrderItemResponse;
import com.champsoft.fooddelivery.orders.api.dto.OrderResponse;
import com.champsoft.fooddelivery.orders.application.port.out.CustomerLookupPort;
import com.champsoft.fooddelivery.orders.application.port.out.DriverAssignmentPort;
import com.champsoft.fooddelivery.orders.application.port.out.RestaurantLookupPort;
import com.champsoft.fooddelivery.orders.domain.model.Order;
import com.champsoft.fooddelivery.orders.domain.model.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderApiMapper {

    private final CustomerLookupPort customerLookupPort;
    private final RestaurantLookupPort restaurantLookupPort;
    private final DriverAssignmentPort driverAssignmentPort;

    public OrderApiMapper(CustomerLookupPort customerLookupPort,
                          RestaurantLookupPort restaurantLookupPort,
                          DriverAssignmentPort driverAssignmentPort) {
        this.customerLookupPort = customerLookupPort;
        this.restaurantLookupPort = restaurantLookupPort;
        this.driverAssignmentPort = driverAssignmentPort;
    }

    public OrderResponse toResponse(Order order) {
        CustomerLookupPort.CustomerSummary customer = customerLookupPort.requireCustomer(order.getCustomerId());
        RestaurantLookupPort.RestaurantSummary restaurant = restaurantLookupPort.requireOrderableRestaurant(
                order.getRestaurantId(),
                order.getItems().stream().map(OrderItem::getMenuItemId).toList()
        );
        DriverAssignmentPort.DriverSummary driver = order.getDriverId() == null ? null : driverAssignmentPort.requireDriver(order.getDriverId());

        return new OrderResponse(
                order.getId(),
                order.getCustomerId(),
                customer.name(),
                order.getRestaurantId(),
                restaurant.name(),
                order.getDriverId(),
                driver == null ? null : driver.name(),
                order.getStatus(),
                order.getTotalPrice(),
                order.getDeliveryAddress().street(),
                order.getDeliveryAddress().city(),
                order.getDeliveryAddress().province(),
                order.getDeliveryAddress().postalCode(),
                order.getPaymentInfo().method(),
                order.getPaymentInfo().lastFourDigits(),
                order.getItems().stream().map(this::toItemResponse).toList()
        );
    }

    private OrderItemResponse toItemResponse(OrderItem item) {
        return new OrderItemResponse(item.getId(), item.getMenuItemId(), item.getQuantity(), item.getPrice());
    }
}
