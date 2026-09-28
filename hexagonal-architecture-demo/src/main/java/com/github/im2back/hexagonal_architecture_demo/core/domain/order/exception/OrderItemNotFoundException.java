package com.github.im2back.hexagonal_architecture_demo.core.domain.order.exception;

public class OrderItemNotFoundException extends RuntimeException {

    public OrderItemNotFoundException(Long itemId) {
        super("Order item not found: " + itemId);
    }
}