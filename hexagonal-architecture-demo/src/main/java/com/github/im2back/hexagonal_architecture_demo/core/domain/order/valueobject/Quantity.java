package com.github.im2back.hexagonal_architecture_demo.core.domain.order.valueobject;

import com.github.im2back.hexagonal_architecture_demo.core.domain.order.exception.InvalidQuantityException;

public record Quantity(int value) {

    public Quantity {

        if (value <= 0) {
            throw new InvalidQuantityException(
                    "Quantity must be greater than zero"
            );
        }
    }
}