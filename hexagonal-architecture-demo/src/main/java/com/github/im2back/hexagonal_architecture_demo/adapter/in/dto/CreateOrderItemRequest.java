package com.github.im2back.hexagonal_architecture_demo.adapter.in.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateOrderItemRequest(
        @NotNull Long productId,
        @NotNull Integer quantity,
        @NotNull BigDecimal unitPrice,
        @NotBlank String currency
) {
}