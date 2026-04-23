package com.champsoft.fooddelivery.orders.application.port.out;

import java.util.UUID;

public interface CustomerLookupPort {

    CustomerSummary requireCustomer(UUID customerId);

    record CustomerSummary(UUID id, String name) {
    }
}
