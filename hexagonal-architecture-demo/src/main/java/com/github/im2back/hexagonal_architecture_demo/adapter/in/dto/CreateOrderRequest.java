package com.github.im2back.hexagonal_architecture_demo.adapter.in.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateOrderRequest(
        @NotNull Long customerId,
        @NotEmpty List<CreateOrderItemRequest> items
) {
}