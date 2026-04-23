package com.champsoft.fooddelivery.drivers.api.dto;

import com.champsoft.fooddelivery.drivers.domain.model.DriverStatus;

import java.util.UUID;

public record DriverResponse(UUID id, String name, String phone, DriverStatus status) {
}
