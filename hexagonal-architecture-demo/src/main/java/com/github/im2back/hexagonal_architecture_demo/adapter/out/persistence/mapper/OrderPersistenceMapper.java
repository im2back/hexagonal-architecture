package com.github.im2back.hexagonal_architecture_demo.adapter.out.persistence.mapper;


import com.github.im2back.hexagonal_architecture_demo.adapter.out.persistence.entity.OrderJpaEntity;
import com.github.im2back.hexagonal_architecture_demo.core.domain.order.model.Order;

public final class OrderPersistenceMapper {

    private OrderPersistenceMapper() {
    }

    public static OrderJpaEntity toEntity(Order order) {
        return new OrderJpaEntity(
                order.getId(),
                order.getCustomerId(),
                order.getStatus()
        );
    }

    public static Order toDomain(OrderJpaEntity entity) {
        return new Order(
                entity.getId(),
                entity.getCustomerId(),
                entity.getStatus()
        );
    }
}