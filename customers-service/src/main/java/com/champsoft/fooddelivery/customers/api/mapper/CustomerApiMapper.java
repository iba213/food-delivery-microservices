package com.champsoft.fooddelivery.customers.api.mapper;

import com.champsoft.fooddelivery.customers.api.dto.CustomerResponse;
import com.champsoft.fooddelivery.customers.domain.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerApiMapper {

    public CustomerResponse toResponse(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail().value(),
                customer.getPhone(),
                customer.getAddress().street(),
                customer.getAddress().city(),
                customer.getAddress().province(),
                customer.getAddress().postalCode()
        );
    }
}
