package com.champsoft.fooddelivery.orders.infrastructure.acl;

import com.champsoft.fooddelivery.orders.application.port.out.CustomerLookupPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
public class CustomerLookupAdapter implements CustomerLookupPort {

    private final RestTemplate restTemplate;
    private final String customerServiceUrl;

    public CustomerLookupAdapter(RestTemplate restTemplate, 
                                 @Value("${app.services.customer.url:http://localhost:8082}") String customerServiceUrl) {
        this.restTemplate = restTemplate;
        this.customerServiceUrl = customerServiceUrl;
    }

    @Override
    public CustomerSummary requireCustomer(UUID customerId) {
        try {
            ResponseEntity<CustomerDto> response = restTemplate.getForEntity(
                    customerServiceUrl + "/api/customers/{id}",
                    CustomerDto.class,
                    customerId
            );
            
            CustomerDto dto = response.getBody();
            if (dto == null) {
                throw new RuntimeException("Customer not found: " + customerId);
            }
            return new CustomerSummary(dto.id(), dto.name());
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("Customer not found: " + customerId, e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to lookup customer: " + customerId, e);
        }
    }

    record CustomerDto(UUID id, String name) {}
}
