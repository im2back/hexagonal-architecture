package com.github.im2back.hexagonal_architecture_demo.adapter.out.persistence.jpa.mapper;

import com.github.im2back.hexagonal_architecture_demo.adapter.out.persistence.jpa.entity.OrderItemJpaEntity;
import com.github.im2back.hexagonal_architecture_demo.adapter.out.persistence.jpa.entity.OrderJpaEntity;
import com.github.im2back.hexagonal_architecture_demo.core.domain.order.model.Order;
import com.github.im2back.hexagonal_architecture_demo.core.domain.order.model.OrderItem;
import com.github.im2back.hexagonal_architecture_demo.core.domain.order.valueobject.Money;
import com.github.im2back.hexagonal_architecture_demo.core.domain.order.valueobject.Quantity;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public final class OrderPersistenceMapper {

    private OrderPersistenceMapper() {
    }

    public static OrderJpaEntity toEntity(Order order) {

        OrderJpaEntity orderEntity = new OrderJpaEntity(
                order.getId(),
                order.getCustomerId(),
                order.getStatus(),
                new ArrayList<>()
        );

        List<OrderItemJpaEntity> itemEntities = order.getItems()
                .stream()
                .map(item -> new OrderItemJpaEntity(
                        item.getId(),
                        item.getProductId(),
                        item.getQuantity().value(),
                        item.getUnitPrice().amount(),
                        item.getUnitPrice().currency().getCurrencyCode(),
                        orderEntity
                ))
                .toList();

        orderEntity.getItems().addAll(itemEntities);

        return orderEntity;
    }

    public static Order toDomain(OrderJpaEntity entity) {

        Order order = new Order(
                entity.getId(),
                entity.getCustomerId(),
                entity.getStatus()
        );

        for (OrderItemJpaEntity itemEntity : entity.getItems()) {

            Quantity quantity = new Quantity(
                    itemEntity.getQuantity()
            );

            Money money = new Money(
                    itemEntity.getUnitPrice(),
                    Currency.getInstance(itemEntity.getCurrency())
            );

            OrderItem item = new OrderItem(
                    itemEntity.getId(),
                    itemEntity.getProductId(),
                    quantity,
                    money
            );

            order.addItem(item);
        }

        return order;
    }
}