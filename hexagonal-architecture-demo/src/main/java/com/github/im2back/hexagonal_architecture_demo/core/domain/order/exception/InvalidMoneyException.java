package com.github.im2back.hexagonal_architecture_demo.core.domain.order.exception;

public class InvalidMoneyException extends RuntimeException {

    public InvalidMoneyException(String message) {
        super(message);
    }
}