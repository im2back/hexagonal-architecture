package com.github.im2back.hexagonal_architecture_demo.core.domain.order.exception;

public class InvalidOrderItemException extends RuntimeException {

    public InvalidOrderItemException(String message) {
        super(message);
    }
}