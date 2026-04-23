package com.champsoft.fooddelivery.restaurants.application.port.out;

import com.champsoft.fooddelivery.restaurants.domain.model.Restaurant;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RestaurantRepositoryPort {

    Restaurant save(Restaurant restaurant);

    List<Restaurant> findAll();

    Optional<Restaurant> findById(UUID id);

    void deleteById(UUID id);
}
