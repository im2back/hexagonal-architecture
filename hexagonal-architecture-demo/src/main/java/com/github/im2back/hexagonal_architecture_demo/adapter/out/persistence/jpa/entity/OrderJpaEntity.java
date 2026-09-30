package com.github.im2back.hexagonal_architecture_demo.adapter.out.persistence.jpa.entity;

import java.util.ArrayList;
import java.util.List;

import com.github.im2back.hexagonal_architecture_demo.core.domain.order.model.OrderStatus;
import jakarta.persistence.*;




@Entity
@Table(name = "orders")
public class OrderJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<OrderItemJpaEntity> items = new ArrayList<>();

    protected OrderJpaEntity() {
    }

    public OrderJpaEntity(
            Long id,
            Long customerId,
            OrderStatus status,
            List<OrderItemJpaEntity> items
    ) {
        this.id = id;
        this.customerId = customerId;
        this.status = status;
        this.items = items;
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

    public List<OrderItemJpaEntity> getItems() {
        return items;
    }
}