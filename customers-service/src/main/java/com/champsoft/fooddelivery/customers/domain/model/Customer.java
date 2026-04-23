package com.champsoft.fooddelivery.customers.domain.model;

import com.champsoft.fooddelivery.customers.domain.exception.InvalidCustomerNameException;

import java.util.UUID;

public class Customer {

    private final UUID id;
    private String name;
    private Email email;
    private String phone;
    private Address address;

    public Customer(UUID id, String name, Email email, String phone, Address address) {
        if (id == null) {
            throw new IllegalArgumentException("Customer id is required");
        }
        this.id = id;
        update(name, email, phone, address);
    }

    public void update(String name, Email email, String phone, Address address) {
        if (name == null || name.isBlank()) {
            throw new InvalidCustomerNameException("Customer name is required");
        }
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Customer phone is required");
        }
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Email getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Address getAddress() {
        return address;
    }
}
