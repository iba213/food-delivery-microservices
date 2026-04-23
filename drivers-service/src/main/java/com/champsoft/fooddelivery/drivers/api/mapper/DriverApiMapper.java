package com.champsoft.fooddelivery.drivers.api.mapper;

import com.champsoft.fooddelivery.drivers.api.dto.DriverResponse;
import com.champsoft.fooddelivery.drivers.domain.model.Driver;
import org.springframework.stereotype.Component;

@Component
public class DriverApiMapper {

    public DriverResponse toResponse(Driver driver) {
        return new DriverResponse(driver.getId(), driver.getName(), driver.getPhone(), driver.getStatus());
    }
}
