package com.champsoft.fooddelivery.customers.domain.exception;

public class InvalidCustomerEmailException extends RuntimeException {

    public InvalidCustomerEmailException(String message) {
        super(message);
    }
}
