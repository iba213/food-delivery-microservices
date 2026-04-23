package com.champsoft.fooddelivery.customers.api.dto;

import java.util.UUID;

public record CustomerResponse(
        UUID id,
        String name,
        String email,
        String phone,
        String street,
        String city,
        String province,
        String postalCode
) {
}
