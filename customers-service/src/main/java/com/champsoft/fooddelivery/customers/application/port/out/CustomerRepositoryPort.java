package com.champsoft.fooddelivery.customers.application.port.out;

import com.champsoft.fooddelivery.customers.domain.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepositoryPort {

    Customer save(Customer customer);

    List<Customer> findAll();

    Optional<Customer> findById(UUID id);

    boolean existsByEmail(String email);

    void deleteById(UUID id);
}
