package com.github.im2back.hexagonal_architecture_demo.configuration;

import com.github.im2back.hexagonal_architecture_demo.core.application.port.in.CreateOrderUseCase;
import com.github.im2back.hexagonal_architecture_demo.core.application.port.out.OrderRepositoryPort;
import com.github.im2back.hexagonal_architecture_demo.core.application.service.CreateOrderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public CreateOrderUseCase createOrderUseCase(
            OrderRepositoryPort orderRepositoryPort
    ) {
        return new CreateOrderService(orderRepositoryPort);
    }
}