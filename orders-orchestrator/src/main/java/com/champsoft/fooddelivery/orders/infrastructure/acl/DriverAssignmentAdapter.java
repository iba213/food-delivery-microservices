package com.champsoft.fooddelivery.orders.infrastructure.acl;

import com.champsoft.fooddelivery.orders.application.port.out.DriverAssignmentPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
public class DriverAssignmentAdapter implements DriverAssignmentPort {

    private final RestTemplate restTemplate;
    private final String driverServiceUrl;

    public DriverAssignmentAdapter(RestTemplate restTemplate,
                                   @Value("${app.services.driver.url:http://localhost:8084}") String driverServiceUrl) {
        this.restTemplate = restTemplate;
        this.driverServiceUrl = driverServiceUrl;
    }

    @Override
    public DriverSummary assignAvailableDriver() {
        ResponseEntity<DriverDto> response = restTemplate.postForEntity(
                driverServiceUrl + "/api/drivers/assign",
                null,
                DriverDto.class
        );
        DriverDto dto = response.getBody();
        if (dto == null) {
            throw new RuntimeException("Could not assign an available driver");
        }
        return new DriverSummary(dto.id(), dto.name(), dto.phone());
    }

    @Override
    public DriverSummary requireDriver(UUID driverId) {
        ResponseEntity<DriverDto> response = restTemplate.getForEntity(
                driverServiceUrl + "/api/drivers/{id}",
                DriverDto.class,
                driverId
        );
        DriverDto dto = response.getBody();
        if (dto == null) {
            throw new RuntimeException("Driver not found: " + driverId);
        }
        return new DriverSummary(dto.id(), dto.name(), dto.phone());
    }

    @Override
    public void markAvailable(UUID driverId) {
        restTemplate.postForEntity(
                driverServiceUrl + "/api/drivers/{id}/available",
                null,
                Void.class,
                driverId
        );
    }

    record DriverDto(UUID id, String name, String phone) {}
}
