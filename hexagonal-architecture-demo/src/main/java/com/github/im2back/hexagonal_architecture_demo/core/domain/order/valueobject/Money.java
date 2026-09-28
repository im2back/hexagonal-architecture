package com.github.im2back.hexagonal_architecture_demo.core.domain.order.valueobject;

import com.github.im2back.hexagonal_architecture_demo.core.domain.order.exception.InvalidMoneyException;

import java.math.BigDecimal;
import java.util.Currency;

public record Money(
        BigDecimal amount,
        Currency currency
) {

    public Money {

        if (amount == null) {
            throw new InvalidMoneyException(
                    "Amount cannot be null"
            );
        }

        if (amount.signum() < 0) {
            throw new InvalidMoneyException(
                    "Amount cannot be negative"
            );
        }

        if (currency == null) {
            throw new InvalidMoneyException(
                    "Currency cannot be null"
            );
        }
    }

    public Money multiply(Quantity quantity) {

        if (quantity == null) {
            throw new InvalidMoneyException(
                    "Quantity cannot be null"
            );
        }

        return new Money(
                amount.multiply(
                        BigDecimal.valueOf(quantity.value())
                ),
                currency
        );
    }

    public Money add(Money other) {

        if (other == null) {
            throw new InvalidMoneyException(
                    "Money to add cannot be null"
            );
        }

        if (!currency.equals(other.currency())) {
            throw new InvalidMoneyException(
                    "Cannot add money with different currencies"
            );
        }

        return new Money(
                amount.add(other.amount()),
                currency
        );
    }
}