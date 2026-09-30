package com.github.im2back.hexagonal_architecture_demo.core.application.port.in;

import com.github.im2back.hexagonal_architecture_demo.core.domain.order.model.Order;
import com.github.im2back.hexagonal_architecture_demo.core.domain.order.model.OrderItem;

import java.util.List;

public interface CreateOrderUseCase {

    Order execute(
            Long customerId,
            List<OrderItem> items
    );
}