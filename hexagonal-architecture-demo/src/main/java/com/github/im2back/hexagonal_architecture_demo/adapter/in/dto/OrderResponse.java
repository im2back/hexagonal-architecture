package com.github.im2back.hexagonal_architecture_demo.adapter.in.dto;

import com.github.im2back.hexagonal_architecture_demo.core.domain.order.model.OrderStatus;

public record OrderResponse(
        Long id,
        Long customerId,
        OrderStatus status
) {
}