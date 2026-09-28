package com.github.im2back.hexagonal_architecture_demo.core.domain.order.exception;

public class InvalidQuantityException extends RuntimeException {

    public InvalidQuantityException(String message) {
        super(message);
    }
}