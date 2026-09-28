package com.github.im2back.hexagonal_architecture_demo.core.domain.order.exception;

public class OrderWithoutItemsException extends RuntimeException {

    public OrderWithoutItemsException() {
        super("Order cannot be confirmed without items");
    }
}