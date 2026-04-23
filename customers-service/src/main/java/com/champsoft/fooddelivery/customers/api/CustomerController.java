package com.champsoft.fooddelivery.customers.api;

import com.champsoft.fooddelivery.customers.api.dto.CreateCustomerRequest;
import com.champsoft.fooddelivery.customers.api.dto.CustomerResponse;
import com.champsoft.fooddelivery.customers.api.dto.UpdateCustomerRequest;
import com.champsoft.fooddelivery.customers.api.mapper.CustomerApiMapper;
import com.champsoft.fooddelivery.customers.application.service.CustomerCrudService;
import com.champsoft.fooddelivery.customers.domain.model.Customer;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerCrudService customerCrudService;
    private final CustomerApiMapper customerApiMapper;

    public CustomerController(CustomerCrudService customerCrudService, CustomerApiMapper customerApiMapper) {
        this.customerCrudService = customerCrudService;
        this.customerApiMapper = customerApiMapper;
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAll() {
        return ResponseEntity.ok(customerCrudService.getAll().stream().map(customerApiMapper::toResponse).toList());
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> getById(@PathVariable UUID customerId) {
        return ResponseEntity.ok(customerApiMapper.toResponse(customerCrudService.getById(customerId)));
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> create(@Valid @RequestBody CreateCustomerRequest request) {
        Customer customer = customerCrudService.create(
                request.name(),
                request.email(),
                request.phone(),
                request.street(),
                request.city(),
                request.province(),
                request.postalCode()
        );
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(customer.getId())
                .toUri();
        return ResponseEntity.created(location).body(customerApiMapper.toResponse(customer));
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> update(@PathVariable UUID customerId, @Valid @RequestBody UpdateCustomerRequest request) {
        return ResponseEntity.ok(customerApiMapper.toResponse(customerCrudService.update(
                customerId,
                request.name(),
                request.email(),
                request.phone(),
                request.street(),
                request.city(),
                request.province(),
                request.postalCode()
        )));
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> delete(@PathVariable UUID customerId) {
        customerCrudService.delete(customerId);
        return ResponseEntity.noContent().build();
    }
}
