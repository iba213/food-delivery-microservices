package com.champsoft.fooddelivery.drivers.api;

import com.champsoft.fooddelivery.drivers.application.exception.DriverConflictException;
import com.champsoft.fooddelivery.drivers.application.exception.DriverNotFoundException;
import com.champsoft.fooddelivery.drivers.web.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice(assignableTypes = DriverController.class)
public class DriverExceptionHandler {

    @ExceptionHandler(DriverNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(DriverNotFoundException exception, HttpServletRequest request) {
        return build(HttpStatus.NOT_FOUND, exception.getMessage(), request);
    }

    @ExceptionHandler(DriverConflictException.class)
    public ResponseEntity<ApiErrorResponse> handleConflict(DriverConflictException exception, HttpServletRequest request) {
        return build(HttpStatus.CONFLICT, exception.getMessage(), request);
    }

    private ResponseEntity<ApiErrorResponse> build(HttpStatus status, String message, HttpServletRequest request) {
        return ResponseEntity.status(status).body(new ApiErrorResponse(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI(),
                List.of()
        ));
    }
}
