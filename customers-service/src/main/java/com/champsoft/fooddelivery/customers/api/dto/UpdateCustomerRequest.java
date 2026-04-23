package com.champsoft.fooddelivery.customers.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateCustomerRequest(
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotBlank String phone,
        @NotBlank String street,
        @NotBlank String city,
        @NotBlank String province,
        @NotBlank String postalCode
) {
}
