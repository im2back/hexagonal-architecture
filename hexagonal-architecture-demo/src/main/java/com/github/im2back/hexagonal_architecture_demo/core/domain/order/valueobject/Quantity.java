package com.github.im2back.hexagonal_architecture_demo.core.domain.order.valueobject;

public record Quantity(int value) {

    public Quantity {

        if (value <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than zero"
            );
        }
    }
}