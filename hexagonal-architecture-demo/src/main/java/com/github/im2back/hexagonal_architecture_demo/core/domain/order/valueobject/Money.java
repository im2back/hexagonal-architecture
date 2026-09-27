package com.github.im2back.hexagonal_architecture_demo.core.domain.order.valueobject;


import java.math.BigDecimal;
import java.util.Currency;

public record Money(
        BigDecimal amount,
        Currency currency
) {

    public Money {

        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }

        if (amount.signum() < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }

        if (currency == null) {
            throw new IllegalArgumentException("Currency cannot be null");
        }
    }
}