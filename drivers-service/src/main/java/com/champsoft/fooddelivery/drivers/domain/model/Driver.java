package com.champsoft.fooddelivery.drivers.domain.model;

import java.util.UUID;

public class Driver {

    private final UUID id;
    private String name;
    private String phone;
    private DriverStatus status;

    public Driver(UUID id, String name, String phone, DriverStatus status) {
        if (id == null) {
            throw new IllegalArgumentException("Driver id is required");
        }
        this.id = id;
        update(name, phone, status);
    }

    public void update(String name, String phone, DriverStatus status) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Driver name is required");
        }
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Driver phone is required");
        }
        if (status == null) {
            throw new IllegalArgumentException("Driver status is required");
        }
        this.name = name;
        this.phone = phone;
        this.status = status;
    }

    public void assignToOrder() {
        if (status != DriverStatus.AVAILABLE) {
            throw new IllegalStateException("Driver is not available for assignment");
        }
        status = DriverStatus.BUSY;
    }

    public void markAvailable() {
        status = DriverStatus.AVAILABLE;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public DriverStatus getStatus() {
        return status;
    }
}
