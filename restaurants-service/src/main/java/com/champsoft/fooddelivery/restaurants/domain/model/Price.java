package com.champsoft.fooddelivery.restaurants.domain.model;

import java.math.BigDecimal;

public record Price(BigDecimal value) {

    public Price {
        if (value == null || value.signum() < 0) {
            throw new IllegalArgumentException("Price must be zero or positive");
        }
    }
}
