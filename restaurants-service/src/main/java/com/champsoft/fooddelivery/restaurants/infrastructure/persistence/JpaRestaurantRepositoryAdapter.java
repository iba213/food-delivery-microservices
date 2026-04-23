package com.champsoft.fooddelivery.restaurants.infrastructure.persistence;

import com.champsoft.fooddelivery.restaurants.application.port.out.RestaurantRepositoryPort;
import com.champsoft.fooddelivery.restaurants.domain.model.MenuItem;
import com.champsoft.fooddelivery.restaurants.domain.model.Price;
import com.champsoft.fooddelivery.restaurants.domain.model.Restaurant;
import com.champsoft.fooddelivery.restaurants.domain.model.RestaurantAddress;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class JpaRestaurantRepositoryAdapter implements RestaurantRepositoryPort {

    private final SpringDataRestaurantRepository repository;

    public JpaRestaurantRepositoryAdapter(SpringDataRestaurantRepository repository) {
        this.repository = repository;
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        RestaurantJpaEntity entity = new RestaurantJpaEntity();
        entity.setId(restaurant.getId());
        entity.setName(restaurant.getName());
        entity.setOpen(restaurant.isOpen());
        RestaurantAddressEmbeddable address = new RestaurantAddressEmbeddable();
        address.setStreet(restaurant.getAddress().street());
        address.setCity(restaurant.getAddress().city());
        address.setProvince(restaurant.getAddress().province());
        address.setPostalCode(restaurant.getAddress().postalCode());
        entity.setAddress(address);
        entity.getMenuItems().clear();
        restaurant.getMenuItems().forEach(item -> {
            MenuItemJpaEntity menuItem = new MenuItemJpaEntity();
            menuItem.setId(item.getId());
            menuItem.setRestaurant(entity);
            menuItem.setName(item.getName());
            menuItem.setPrice(item.getPrice().value());
            menuItem.setAvailable(item.isAvailable());
            entity.getMenuItems().add(menuItem);
        });
        return toDomain(repository.save(entity));
    }

    @Override
    public List<Restaurant> findAll() {
        return repository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public Optional<Restaurant> findById(UUID id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    private Restaurant toDomain(RestaurantJpaEntity entity) {
        return new Restaurant(
                entity.getId(),
                entity.getName(),
                new RestaurantAddress(
                        entity.getAddress().getStreet(),
                        entity.getAddress().getCity(),
                        entity.getAddress().getProvince(),
                        entity.getAddress().getPostalCode()
                ),
                entity.isOpen(),
                entity.getMenuItems().stream()
                        .map(item -> new MenuItem(item.getId(), item.getName(), new Price(item.getPrice()), item.isAvailable()))
                        .toList()
        );
    }
}
