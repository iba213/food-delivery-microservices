package com.champsoft.fooddelivery.drivers.api.dto;

import com.champsoft.fooddelivery.drivers.domain.model.DriverStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateDriverRequest(
        @NotBlank String name,
        @NotBlank String phone,
        @NotNull DriverStatus status
) {
}
