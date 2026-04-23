package com.champsoft.fooddelivery.customers.domain.model;

import com.champsoft.fooddelivery.customers.domain.exception.InvalidCustomerEmailException;

public record Email(String value) {

    public Email {
        if (value == null || value.isBlank() || !value.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new InvalidCustomerEmailException("Customer email must be valid");
        }
    }
}
