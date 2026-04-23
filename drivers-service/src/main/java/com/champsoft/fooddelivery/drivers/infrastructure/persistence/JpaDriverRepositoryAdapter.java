package com.champsoft.fooddelivery.drivers.infrastructure.persistence;

import com.champsoft.fooddelivery.drivers.application.port.out.DriverRepositoryPort;
import com.champsoft.fooddelivery.drivers.domain.model.Driver;
import com.champsoft.fooddelivery.drivers.domain.model.DriverStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class JpaDriverRepositoryAdapter implements DriverRepositoryPort {

    private final SpringDataDriverRepository repository;

    public JpaDriverRepositoryAdapter(SpringDataDriverRepository repository) {
        this.repository = repository;
    }

    @Override
    public Driver save(Driver driver) {
        DriverJpaEntity entity = new DriverJpaEntity();
        entity.setId(driver.getId());
        entity.setName(driver.getName());
        entity.setPhone(driver.getPhone());
        entity.setStatus(driver.getStatus());
        return toDomain(repository.save(entity));
    }

    @Override
    public List<Driver> findAll() {
        return repository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public Optional<Driver> findById(UUID id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Driver> findFirstByStatus(DriverStatus status) {
        return repository.findFirstByStatus(status).map(this::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    private Driver toDomain(DriverJpaEntity entity) {
        return new Driver(entity.getId(), entity.getName(), entity.getPhone(), entity.getStatus());
    }
}
