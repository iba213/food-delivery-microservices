package com.champsoft.fooddelivery.customers.persistence;

import com.champsoft.fooddelivery.customers.application.port.out.CustomerRepositoryPort;
import com.champsoft.fooddelivery.customers.domain.model.Address;
import com.champsoft.fooddelivery.customers.domain.model.Customer;
import com.champsoft.fooddelivery.customers.domain.model.Email;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class JpaCustomerRepositoryAdapter implements CustomerRepositoryPort {

    private final SpringDataCustomerRepository repository;

    public JpaCustomerRepositoryAdapter(SpringDataCustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Customer save(Customer customer) {
        CustomerJpaEntity entity = new CustomerJpaEntity();
        entity.setId(customer.getId());
        entity.setName(customer.getName());
        entity.setEmail(customer.getEmail().value());
        entity.setPhone(customer.getPhone());
        AddressEmbeddable address = new AddressEmbeddable();
        address.setStreet(customer.getAddress().street());
        address.setCity(customer.getAddress().city());
        address.setProvince(customer.getAddress().province());
        address.setPostalCode(customer.getAddress().postalCode());
        entity.setAddress(address);
        return toDomain(repository.save(entity));
    }

    @Override
    public List<Customer> findAll() {
        return repository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public Optional<Customer> findById(UUID id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmailIgnoreCase(email);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    private Customer toDomain(CustomerJpaEntity entity) {
        return new Customer(
                entity.getId(),
                entity.getName(),
                new Email(entity.getEmail()),
                entity.getPhone(),
                new Address(
                        entity.getAddress().getStreet(),
                        entity.getAddress().getCity(),
                        entity.getAddress().getProvince(),
                        entity.getAddress().getPostalCode()
                )
        );
    }
}
