package com.champsoft.fooddelivery.orders.infrastructure.acl;

import com.champsoft.fooddeliveryplatform.modules.drivers.application.service.DriverCrudService;
import com.champsoft.fooddeliveryplatform.modules.drivers.domain.model.Driver;
import com.champsoft.fooddeliveryplatform.modules.orders.application.port.out.DriverAssignmentPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DriverAssignmentAdapter implements DriverAssignmentPort {

    private final DriverCrudService driverCrudService;

    public DriverAssignmentAdapter(DriverCrudService driverCrudService) {
        this.driverCrudService = driverCrudService;
    }

    @Override
    public DriverSummary assignAvailableDriver() {
        Driver driver = driverCrudService.assignAvailableDriver();
        return new DriverSummary(driver.getId(), driver.getName(), driver.getPhone());
    }

    @Override
    public DriverSummary requireDriver(UUID driverId) {
        Driver driver = driverCrudService.getById(driverId);
        return new DriverSummary(driver.getId(), driver.getName(), driver.getPhone());
    }

    @Override
    public void markAvailable(UUID driverId) {
        driverCrudService.markAvailable(driverId);
    }
}
