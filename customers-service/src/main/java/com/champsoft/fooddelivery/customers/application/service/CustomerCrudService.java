package com.champsoft.fooddelivery.customers.application.service;

import com.champsoft.fooddelivery.customers.application.exception.CustomerNotFoundException;
import com.champsoft.fooddelivery.customers.application.exception.DuplicateCustomerException;
import com.champsoft.fooddelivery.customers.application.port.out.CustomerRepositoryPort;
import com.champsoft.fooddelivery.customers.domain.model.Address;
import com.champsoft.fooddelivery.customers.domain.model.Customer;
import com.champsoft.fooddelivery.customers.domain.model.Email;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CustomerCrudService {

    private final CustomerRepositoryPort customerRepositoryPort;

    public CustomerCrudService(CustomerRepositoryPort customerRepositoryPort) {
        this.customerRepositoryPort = customerRepositoryPort;
    }

    public Customer create(String name, String email, String phone, String street, String city, String province, String postalCode) {
        if (customerRepositoryPort.existsByEmail(email)) {
            throw new DuplicateCustomerException("A customer with this email already exists");
        }
        return customerRepositoryPort.save(new Customer(
                UUID.randomUUID(),
                name,
                new Email(email),
                phone,
                new Address(street, city, province, postalCode)
        ));
    }

    @Transactional(readOnly = true)
    public List<Customer> getAll() {
        return customerRepositoryPort.findAll();
    }

    @Transactional(readOnly = true)
    public Customer getById(UUID id) {
        return customerRepositoryPort.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found: " + id));
    }

    public Customer update(UUID id, String name, String email, String phone, String street, String city, String province, String postalCode) {
        Customer customer = getById(id);
        if (!customer.getEmail().value().equalsIgnoreCase(email) && customerRepositoryPort.existsByEmail(email)) {
            throw new DuplicateCustomerException("A customer with this email already exists");
        }
        customer.update(name, new Email(email), phone, new Address(street, city, province, postalCode));
        return customerRepositoryPort.save(customer);
    }

    public void delete(UUID id) {
        getById(id);
        customerRepositoryPort.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Customer requireExisting(UUID id) {
        return getById(id);
    }
}
