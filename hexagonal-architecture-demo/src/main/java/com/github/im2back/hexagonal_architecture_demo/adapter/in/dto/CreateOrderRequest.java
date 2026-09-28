package com.github.im2back.hexagonal_architecture_demo.adapter.in.dto;


import jakarta.validation.constraints.NotNull;

public record CreateOrderRequest(

        @NotNull(message = "Customer id cannot be null")
        Long customerId

) {
}