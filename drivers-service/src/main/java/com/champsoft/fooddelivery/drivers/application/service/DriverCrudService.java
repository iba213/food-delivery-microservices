package com.champsoft.fooddelivery.drivers.application.service;

import com.champsoft.fooddelivery.drivers.application.exception.DriverConflictException;
import com.champsoft.fooddelivery.drivers.application.exception.DriverNotFoundException;
import com.champsoft.fooddelivery.drivers.application.port.out.DriverRepositoryPort;
import com.champsoft.fooddelivery.drivers.domain.model.Driver;
import com.champsoft.fooddelivery.drivers.domain.model.DriverStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class DriverCrudService {

    private final DriverRepositoryPort driverRepositoryPort;

    public DriverCrudService(DriverRepositoryPort driverRepositoryPort) {
        this.driverRepositoryPort = driverRepositoryPort;
    }

    public Driver create(String name, String phone, DriverStatus status) {
        return driverRepositoryPort.save(new Driver(UUID.randomUUID(), name, phone, status));
    }

    @Transactional(readOnly = true)
    public List<Driver> getAll() {
        return driverRepositoryPort.findAll();
    }

    @Transactional(readOnly = true)
    public Driver getById(UUID id) {
        return driverRepositoryPort.findById(id)
                .orElseThrow(() -> new DriverNotFoundException("Driver not found: " + id));
    }

    public Driver update(UUID id, String name, String phone, DriverStatus status) {
        Driver driver = getById(id);
        driver.update(name, phone, status);
        return driverRepositoryPort.save(driver);
    }

    public void delete(UUID id) {
        getById(id);
        driverRepositoryPort.deleteById(id);
    }

    public Driver assignAvailableDriver() {
        Driver driver = driverRepositoryPort.findFirstByStatus(DriverStatus.AVAILABLE)
                .orElseThrow(() -> new DriverConflictException("No available driver could be assigned"));
        try {
            driver.assignToOrder();
        } catch (IllegalStateException exception) {
            throw new DriverConflictException(exception.getMessage());
        }
        return driverRepositoryPort.save(driver);
    }

    public void markAvailable(UUID id) {
        Driver driver = getById(id);
        driver.markAvailable();
        driverRepositoryPort.save(driver);
    }
}
