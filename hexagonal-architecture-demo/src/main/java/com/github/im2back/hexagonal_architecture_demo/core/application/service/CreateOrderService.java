package com.github.im2back.hexagonal_architecture_demo.core.application.service;

import com.github.im2back.hexagonal_architecture_demo.core.application.port.in.CreateOrderUseCase;
import com.github.im2back.hexagonal_architecture_demo.core.application.port.out.OrderRepositoryPort;
import com.github.im2back.hexagonal_architecture_demo.core.domain.order.model.Order;

public class CreateOrderService implements CreateOrderUseCase {

    private final OrderRepositoryPort orderRepositoryPort;

    public CreateOrderService(
            OrderRepositoryPort orderRepositoryPort
    ) {
        this.orderRepositoryPort = orderRepositoryPort;
    }

    @Override
    public Order execute(Long customerId) {

        Order order = new Order(
                null,
                customerId
        );

        return orderRepositoryPort.save(order);
    }
}