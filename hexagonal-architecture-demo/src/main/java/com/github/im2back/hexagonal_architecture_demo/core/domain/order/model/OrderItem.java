package com.github.im2back.hexagonal_architecture_demo.core.domain.order.model;

import com.github.im2back.hexagonal_architecture_demo.core.domain.order.valueobject.Money;
import com.github.im2back.hexagonal_architecture_demo.core.domain.order.valueobject.Quantity;

public class  OrderItem{

    private Long id;
    private Long productId;
    private Quantity quantity;
    private Money unitPrice;

    public OrderItem(
            Long id,
            Long productId,
            Quantity quantity,
            Money unitPrice
    ) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public Money getUnitPrice() {
        return unitPrice;
    }
}