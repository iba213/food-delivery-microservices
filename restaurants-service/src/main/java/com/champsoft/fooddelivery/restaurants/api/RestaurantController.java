package com.champsoft.fooddelivery.restaurants.api;

import com.champsoft.fooddelivery.restaurants.api.dto.CreateRestaurantRequest;
import com.champsoft.fooddelivery.restaurants.api.dto.RestaurantResponse;
import com.champsoft.fooddelivery.restaurants.api.dto.UpdateRestaurantRequest;
import com.champsoft.fooddelivery.restaurants.api.mapper.RestaurantApiMapper;
import com.champsoft.fooddelivery.restaurants.application.service.RestaurantCrudService;
import com.champsoft.fooddelivery.restaurants.domain.model.Restaurant;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantCrudService restaurantCrudService;
    private final RestaurantApiMapper restaurantApiMapper;

    public RestaurantController(RestaurantCrudService restaurantCrudService, RestaurantApiMapper restaurantApiMapper) {
        this.restaurantCrudService = restaurantCrudService;
        this.restaurantApiMapper = restaurantApiMapper;
    }

    @GetMapping
    public ResponseEntity<List<RestaurantResponse>> getAll() {
        return ResponseEntity.ok(restaurantCrudService.getAll().stream().map(restaurantApiMapper::toResponse).toList());
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantResponse> getById(@PathVariable UUID restaurantId) {
        return ResponseEntity.ok(restaurantApiMapper.toResponse(restaurantCrudService.getById(restaurantId)));
    }

    @PostMapping
    public ResponseEntity<RestaurantResponse> create(@Valid @RequestBody CreateRestaurantRequest request) {
        Restaurant restaurant = restaurantCrudService.create(
                request.name(),
                request.street(),
                request.city(),
                request.province(),
                request.postalCode(),
                request.open(),
                request.menuItems()
        );
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(restaurant.getId())
                .toUri();
        return ResponseEntity.created(location).body(restaurantApiMapper.toResponse(restaurant));
    }

    @PutMapping("/{restaurantId}")
    public ResponseEntity<RestaurantResponse> update(@PathVariable UUID restaurantId, @Valid @RequestBody UpdateRestaurantRequest request) {
        return ResponseEntity.ok(restaurantApiMapper.toResponse(restaurantCrudService.update(
                restaurantId,
                request.name(),
                request.street(),
                request.city(),
                request.province(),
                request.postalCode(),
                request.open(),
                request.menuItems()
        )));
    }

    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<Void> delete(@PathVariable UUID restaurantId) {
        restaurantCrudService.delete(restaurantId);
        return ResponseEntity.noContent().build();
    }
}
