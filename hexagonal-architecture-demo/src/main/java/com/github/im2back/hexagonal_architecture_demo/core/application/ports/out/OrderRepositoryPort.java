package com.github.im2back.hexagonal_architecture_demo.core.application.ports.out;


import com.github.im2back.hexagonal_architecture_demo.core.domain.order.model.Order;

public interface OrderRepositoryPort {

    Order save(Order order);
}