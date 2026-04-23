package com.champsoft.fooddelivery.restaurants.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataRestaurantRepository extends JpaRepository<RestaurantJpaEntity, UUID> {
}
