package com.github.im2back.hexagonal_architecture_demo.core.domain.order.model;

import com.github.im2back.hexagonal_architecture_demo.core.domain.order.valueobject.Money;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public void addItem(OrderItem item) {

        if (status != OrderStatus.CREATED) {
            throw new IllegalStateException(
                    "Only CREATED orders can receive items"
            );
        }

        if (item == null) {
            throw new IllegalArgumentException(
                    "Order item cannot be null"
            );
        }

        items.add(item);
    }

    public void removeItem(Long itemId) {

        if (status != OrderStatus.CREATED) {
            throw new IllegalStateException(
                    "Only CREATED orders can have items removed"
            );
        }

        boolean removed = items.removeIf(
                item -> item.getId().equals(itemId)
        );

        if (!removed) {
            throw new IllegalArgumentException(
                    "Order item not found"
            );
        }
    }

    public void confirm() {

        if (status != OrderStatus.CREATED) {
            throw new IllegalStateException(
                    "Only CREATED orders can be confirmed"
            );
        }

        if (items.isEmpty()) {
            throw new IllegalStateException(
                    "Order cannot be confirmed without items"
            );
        }

        this.status = OrderStatus.CONFIRMED;
    }

    public void cancel() {

        if (status != OrderStatus.CREATED) {
            throw new IllegalStateException(
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