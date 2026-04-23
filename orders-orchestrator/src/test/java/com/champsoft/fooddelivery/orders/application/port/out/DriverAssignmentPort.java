package com.champsoft.fooddelivery.orders.application.port.out;

import java.util.UUID;

public interface DriverAssignmentPort {

    DriverSummary assignAvailableDriver();

    DriverSummary requireDriver(UUID driverId);

    void markAvailable(UUID driverId);

    record DriverSummary(UUID id, String name, String phone) {
    }
}
