package com.champsoft.fooddelivery.drivers.application.port.out;

import com.champsoft.fooddelivery.drivers.domain.model.Driver;
import com.champsoft.fooddelivery.drivers.domain.model.DriverStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DriverRepositoryPort {

    Driver save(Driver driver);

    List<Driver> findAll();

    Optional<Driver> findById(UUID id);

    Optional<Driver> findFirstByStatus(DriverStatus status);

    void deleteById(UUID id);
}
