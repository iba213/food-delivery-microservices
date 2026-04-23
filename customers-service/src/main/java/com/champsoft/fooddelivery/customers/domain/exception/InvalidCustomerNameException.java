package com.champsoft.fooddelivery.customers.domain.exception;

public class InvalidCustomerNameException extends RuntimeException {

    public InvalidCustomerNameException(String message) {
        super(message);
    }
}
