package com.champsoft.fooddelivery.orders.application.port.out;

import com.champsoft.fooddelivery.orders.domain.model.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepositoryPort {

    Order save(Order order);

    List<Order> findAll();

    Optional<Order> findById(UUID id);

    void deleteById(UUID id);
}
