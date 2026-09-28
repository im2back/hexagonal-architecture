package com.github.im2back.hexagonal_architecture_demo.adapter.in.mapper;

import com.github.im2back.hexagonal_architecture_demo.adapter.in.dto.OrderResponse;
import com.github.im2back.hexagonal_architecture_demo.core.domain.order.model.Order;

public final class OrderRestMapper {

    private OrderRestMapper() {
    }

    public static OrderResponse toResponse(Order order) {

        return new OrderResponse(
                order.getId(),
                order.getCustomerId(),
                order.getStatus()
        );
    }
}