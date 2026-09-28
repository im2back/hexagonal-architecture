package com.github.im2back.hexagonal_architecture_demo.adapter.in.rest;

import com.github.im2back.hexagonal_architecture_demo.adapter.in.dto.CreateOrderRequest;
import com.github.im2back.hexagonal_architecture_demo.adapter.in.dto.OrderResponse;
import com.github.im2back.hexagonal_architecture_demo.adapter.in.mapper.OrderRestMapper;
import com.github.im2back.hexagonal_architecture_demo.core.application.port.in.CreateOrderUseCase;
import com.github.im2back.hexagonal_architecture_demo.core.domain.order.model.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.github.im2back.hexagonal_architecture_demo.core.application.port.in.CreateOrderUseCase;
import com.github.im2back.hexagonal_architecture_demo.core.domain.order.model.Order;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;

    public OrderController(CreateOrderUseCase createOrderUseCase) {
        this.createOrderUseCase = createOrderUseCase;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(
            @Valid @RequestBody CreateOrderRequest request
    ) {

        Order order = createOrderUseCase.execute(
                request.customerId()
        );

        OrderResponse response =
                OrderRestMapper.toResponse(order);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
