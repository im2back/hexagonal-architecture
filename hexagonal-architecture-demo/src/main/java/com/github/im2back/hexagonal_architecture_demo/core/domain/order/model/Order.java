package com.github.im2back.hexagonal_architecture_demo.core.domain.order.model;


public class Order {

    private Long id;
    private Long customerId;
    private OrderStatus status;

    public Order(Long id, Long customerId) {
        this.id = id;
        this.customerId = customerId;
        this.status = OrderStatus.CREATED;
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
}