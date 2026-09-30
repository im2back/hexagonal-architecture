package com.github.im2back.hexagonal_architecture_demo.adapter.in.mapper;

import com.github.im2back.hexagonal_architecture_demo.adapter.in.dto.CreateOrderRequest;
import com.github.im2back.hexagonal_architecture_demo.adapter.in.dto.OrderResponse;
import com.github.im2back.hexagonal_architecture_demo.core.domain.order.model.Order;
import com.github.im2back.hexagonal_architecture_demo.core.domain.order.model.OrderItem;
import com.github.im2back.hexagonal_architecture_demo.core.domain.order.valueobject.Money;
import com.github.im2back.hexagonal_architecture_demo.core.domain.order.valueobject.Quantity;

import java.util.Currency;
import java.util.List;

public final class OrderRestMapper {

    private OrderRestMapper() {
    }

    public static List<OrderItem> toDomainItems(
            CreateOrderRequest request
    ) {

        return request.items()
                .stream()
                .map(item -> new OrderItem(
                        null,
                        item.productId(),
                        new Quantity(item.quantity()),
                        new Money(
                                item.unitPrice(),
                                Currency.getInstance(item.currency())
                        )
                ))
                .toList();
    }

    public static OrderResponse toResponse(Order order) {

        return new OrderResponse(
                order.getId(),
                order.getCustomerId(),
                order.getStatus()
        );
    }
}