package com.champsoft.fooddelivery.orders.api;

import com.champsoft.fooddelivery.orders.api.dto.CreateOrderRequest;
import com.champsoft.fooddelivery.orders.api.dto.OrderResponse;
import com.champsoft.fooddelivery.orders.api.dto.UpdateOrderRequest;
import com.champsoft.fooddelivery.orders.api.mapper.OrderApiMapper;
import com.champsoft.fooddelivery.orders.application.service.OrderCrudService;
import com.champsoft.fooddelivery.orders.domain.model.Order;
import com.champsoft.fooddelivery.orders.domain.model.OrderStatus;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderCrudService orderCrudService;
    private final OrderApiMapper orderApiMapper;

    public OrderController(OrderCrudService orderCrudService, OrderApiMapper orderApiMapper) {
        this.orderCrudService = orderCrudService;
        this.orderApiMapper = orderApiMapper;
    }

    @GetMapping
    public ResponseEntity<List<EntityModel<OrderResponse>>> getAll() {
        return ResponseEntity.ok(orderCrudService.getAll().stream().map(this::toModel).toList());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<EntityModel<OrderResponse>> getById(@PathVariable UUID orderId) {
        return ResponseEntity.ok(toModel(orderCrudService.getById(orderId)));
    }

    @PostMapping
    public ResponseEntity<EntityModel<OrderResponse>> create(@Valid @RequestBody CreateOrderRequest request) {
        Order order = orderCrudService.create(
                request.customerId(),
                request.restaurantId(),
                request.street(),
                request.city(),
                request.province(),
                request.postalCode(),
                request.paymentMethod(),
                request.paymentLastFourDigits(),
                request.items()
        );
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(order.getId())
                .toUri();
        return ResponseEntity.created(location).body(toModel(order));
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<EntityModel<OrderResponse>> update(@PathVariable UUID orderId, @Valid @RequestBody UpdateOrderRequest request) {
        return ResponseEntity.ok(toModel(orderCrudService.update(
                orderId,
                request.street(),
                request.city(),
                request.province(),
                request.postalCode(),
                request.paymentMethod(),
                request.paymentLastFourDigits(),
                request.items()
        )));
    }

    @PostMapping("/{orderId}/deliver")
    public ResponseEntity<EntityModel<OrderResponse>> deliver(@PathVariable UUID orderId) {
        return ResponseEntity.ok(toModel(orderCrudService.deliver(orderId)));
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<EntityModel<OrderResponse>> cancel(@PathVariable UUID orderId) {
        return ResponseEntity.ok(toModel(orderCrudService.cancel(orderId)));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> delete(@PathVariable UUID orderId) {
        orderCrudService.delete(orderId);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<OrderResponse> toModel(Order order) {
        EntityModel<OrderResponse> model = EntityModel.of(orderApiMapper.toResponse(order));
        model.add(linkTo(methodOn(OrderController.class).getById(order.getId())).withSelfRel());
        model.add(linkTo(methodOn(OrderController.class).getAll()).withRel("orders"));
        if (order.getStatus() == OrderStatus.ASSIGNED) {
            model.add(linkTo(methodOn(OrderController.class).deliver(order.getId())).withRel("deliver"));
        }
        if (order.getStatus() == OrderStatus.CREATED || order.getStatus() == OrderStatus.ASSIGNED) {
            model.add(linkTo(methodOn(OrderController.class).cancel(order.getId())).withRel("cancel"));
        }
        return model;
    }
}
