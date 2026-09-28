package com.github.im2back.hexagonal_architecture_demo.core.domain.order.model;

import com.github.im2back.hexagonal_architecture_demo.core.domain.order.valueobject.Money;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.github.im2back.hexagonal_architecture_demo.core.domain.order.exception.InvalidOrderItemException;
import com.github.im2back.hexagonal_architecture_demo.core.domain.order.exception.InvalidOrderStatusException;
import com.github.im2back.hexagonal_architecture_demo.core.domain.order.exception.OrderItemNotFoundException;
import com.github.im2back.hexagonal_architecture_demo.core.domain.order.exception.OrderWithoutItemsException;
public class Order {

    private Long id;
    private Long customerId;
    private OrderStatus status;
    private final List<OrderItem> items;

    public Order(Long id, Long customerId) {
        this.id = id;
        this.customerId = customerId;
        this.status = OrderStatus.CREATED;
        this.items = new ArrayList<>();
    }
    public Order(
            Long id,
            Long customerId,
            OrderStatus status
    ) {
        this.id = id;
        this.customerId = customerId;
        this.status = status;
        this.items = new ArrayList<>();
    }
    public void addItem(OrderItem item) {

        if (status != OrderStatus.CREATED) {
            throw new InvalidOrderStatusException(
                    "Only CREATED orders can receive items"
            );
        }

        if (item == null) {
            throw new InvalidOrderItemException(
                    "Order item cannot be null"
            );
        }

        items.add(item);
    }

    public void removeItem(Long itemId) {

        if (status != OrderStatus.CREATED) {
            throw new InvalidOrderStatusException(
                    "Only CREATED orders can have items removed"
            );
        }

        boolean removed = items.removeIf(
                item -> item.getId().equals(itemId)
        );

        if (!removed) {
            throw new OrderItemNotFoundException(itemId);
        }
    }

    public void confirm() {

        if (status != OrderStatus.CREATED) {
            throw new InvalidOrderStatusException(
                    "Only CREATED orders can be confirmed"
            );
        }

        if (items.isEmpty()) {
            throw new OrderWithoutItemsException();
        }

        this.status = OrderStatus.CONFIRMED;
    }

    public void cancel() {

        if (status != OrderStatus.CREATED) {
            throw new InvalidOrderStatusException(
                    "Only CREATED orders can be cancelled"
            );
        }

        this.status = OrderStatus.CANCELLED;
    }

    public Money getTotal() {

        if (items.isEmpty()) {
            return null;
        }

        return items.stream()
                .map(OrderItem::getSubtotal)
                .reduce(Money::add)
                .orElseThrow();
    }

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }
}