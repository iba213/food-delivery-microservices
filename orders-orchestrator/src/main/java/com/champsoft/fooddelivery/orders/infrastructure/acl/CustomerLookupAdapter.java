package com.champsoft.fooddelivery.orders.infrastructure.acl;

import com.champsoft.fooddelivery.customers.application.service.CustomerCrudService;
import com.champsoft.fooddelivery.customers.domain.model.Customer;
import com.champsoft.fooddelivery.orders.application.port.out.CustomerLookupPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomerLookupAdapter implements CustomerLookupPort {

    private final CustomerCrudService customerCrudService;

    public CustomerLookupAdapter(CustomerCrudService customerCrudService) {
        this.customerCrudService = customerCrudService;
    }

    @Override
    public CustomerSummary requireCustomer(UUID customerId) {
        Customer customer = customerCrudService.requireExisting(customerId);
        return new CustomerSummary(customer.getId(), customer.getName());
    }
}
