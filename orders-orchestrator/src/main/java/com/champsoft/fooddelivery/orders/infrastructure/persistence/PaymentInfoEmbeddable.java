package com.champsoft.fooddelivery.orders.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class PaymentInfoEmbeddable {

    @Column(name = "payment_method", nullable = false)
    private String method;

    @Column(name = "payment_last_four_digits", nullable = false)
    private String lastFourDigits;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getLastFourDigits() {
        return lastFourDigits;
    }

    public void setLastFourDigits(String lastFourDigits) {
        this.lastFourDigits = lastFourDigits;
    }
}
