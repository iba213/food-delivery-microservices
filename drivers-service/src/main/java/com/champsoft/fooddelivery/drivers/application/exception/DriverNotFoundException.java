package com.champsoft.fooddelivery.drivers.application.exception;

public class DriverNotFoundException extends RuntimeException {

    public DriverNotFoundException(String message) {
        super(message);
    }
}
