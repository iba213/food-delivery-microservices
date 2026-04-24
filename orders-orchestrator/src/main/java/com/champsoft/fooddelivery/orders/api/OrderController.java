package com.champsoft.fooddelivery.orders.api;

import com.champsoft.fooddelivery.orders.api.dto.CreateOrderRequest;
import com.champsoft.fooddelivery.orders.api.dto.OrderResponse;
import com.champsoft.fooddelivery.orders.api.dto.UpdateOrderRequest;
import com.champsoft.fooddelivery.orders.api.mapper.OrderApiMapper;
import com.champsoft.fooddelivery.orders.application.service.OrderOrchestratorService;
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

    private final OrderOrchestratorService orderOrchestratorService;
    private final OrderCrudService orderCrudService;
    private final OrderApiMapper orderApiMapper;

    public OrderController(OrderOrchestratorService orderOrchestratorService,
                           OrderCrudService orderCrudService,
                           OrderApiMapper orderApiMapper) {
        this.orderOrchestratorService = orderOrchestratorService;
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
        // COORDINATION: The Orchestrator validates the Customer and Restaurant via HTTP [cite: 169-173]
        Order order = orderOrchestratorService.processOrder(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(order.getId())
                .toUri();
        return ResponseEntity.created(location).body(toModel(order));
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<EntityModel<OrderResponse>> cancel(@PathVariable UUID orderId) {
        return ResponseEntity.ok(toModel(orderCrudService.cancel(orderId)));
    }

    @PostMapping("/{orderId}/deliver")
    public ResponseEntity<EntityModel<OrderResponse>> deliver(@PathVariable UUID orderId) {
        return ResponseEntity.ok(toModel(orderCrudService.deliver(orderId)));
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<EntityModel<OrderResponse>> update(@PathVariable UUID orderId, @Valid @RequestBody UpdateOrderRequest request) {
        Order order = orderCrudService.update(orderId, request.street(), request.city(), request.province(), request.postalCode(), request.paymentMethod(), request.paymentLastFourDigits(), request.items());
        return ResponseEntity.ok(toModel(order));
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

        // HATEOAS: Logic-based links based on state [cite: 15, 20]
        if (order.getStatus() == OrderStatus.ASSIGNED) {
            model.add(linkTo(methodOn(OrderController.class).deliver(order.getId())).withRel("deliver"));
        }
        if (order.getStatus() == OrderStatus.CREATED || order.getStatus() == OrderStatus.ASSIGNED) {
            model.add(linkTo(methodOn(OrderController.class).cancel(order.getId())).withRel("cancel"));
        }
        return model;
    }
}
