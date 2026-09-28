package com.github.im2back.hexagonal_architecture_demo.core.domain.order.exception;

public class InvalidOrderStatusException extends RuntimeException {

    public InvalidOrderStatusException(String message) {
        super(message);
    }
}