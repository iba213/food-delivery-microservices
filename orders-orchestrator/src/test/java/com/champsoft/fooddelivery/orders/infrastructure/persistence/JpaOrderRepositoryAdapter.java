package com.champsoft.fooddelivery.orders.infrastructure.persistence;

import com.champsoft.fooddeliveryplatform.modules.orders.application.port.out.OrderRepositoryPort;
import com.champsoft.fooddeliveryplatform.modules.orders.domain.model.DeliveryAddress;
import com.champsoft.fooddeliveryplatform.modules.orders.domain.model.Order;
import com.champsoft.fooddeliveryplatform.modules.orders.domain.model.OrderItem;
import com.champsoft.fooddeliveryplatform.modules.orders.domain.model.PaymentInfo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class JpaOrderRepositoryAdapter implements OrderRepositoryPort {

    private final SpringDataOrderRepository repository;

    public JpaOrderRepositoryAdapter(SpringDataOrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Order save(Order order) {
        OrderJpaEntity entity = new OrderJpaEntity();
        entity.setId(order.getId());
        entity.setCustomerId(order.getCustomerId());
        entity.setRestaurantId(order.getRestaurantId());
        entity.setDriverId(order.getDriverId());
        entity.setStatus(order.getStatus());
        entity.setTotalPrice(order.getTotalPrice());

        DeliveryAddressEmbeddable address = new DeliveryAddressEmbeddable();
        address.setStreet(order.getDeliveryAddress().street());
        address.setCity(order.getDeliveryAddress().city());
        address.setProvince(order.getDeliveryAddress().province());
        address.setPostalCode(order.getDeliveryAddress().postalCode());
        entity.setDeliveryAddress(address);

        PaymentInfoEmbeddable payment = new PaymentInfoEmbeddable();
        payment.setMethod(order.getPaymentInfo().method());
        payment.setLastFourDigits(order.getPaymentInfo().lastFourDigits());
        entity.setPaymentInfo(payment);

        entity.getItems().clear();
        order.getItems().forEach(item -> {
            OrderItemJpaEntity itemEntity = new OrderItemJpaEntity();
            itemEntity.setId(item.getId());
            itemEntity.setOrder(entity);
            itemEntity.setMenuItemId(item.getMenuItemId());
            itemEntity.setQuantity(item.getQuantity());
            itemEntity.setPrice(item.getPrice());
            entity.getItems().add(itemEntity);
        });

        return toDomain(repository.save(entity));
    }

    @Override
    public List<Order> findAll() {
        return repository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    private Order toDomain(OrderJpaEntity entity) {
        return new Order(
                entity.getId(),
                entity.getCustomerId(),
                entity.getRestaurantId(),
                entity.getDriverId(),
                entity.getStatus(),
                new DeliveryAddress(
                        entity.getDeliveryAddress().getStreet(),
                        entity.getDeliveryAddress().getCity(),
                        entity.getDeliveryAddress().getProvince(),
                        entity.getDeliveryAddress().getPostalCode()
                ),
                new PaymentInfo(
                        entity.getPaymentInfo().getMethod(),
                        entity.getPaymentInfo().getLastFourDigits()
                ),
                entity.getItems().stream()
                        .map(item -> new OrderItem(item.getId(), item.getMenuItemId(), item.getQuantity(), item.getPrice()))
                        .toList(),
                entity.getTotalPrice()
        );
    }
}
