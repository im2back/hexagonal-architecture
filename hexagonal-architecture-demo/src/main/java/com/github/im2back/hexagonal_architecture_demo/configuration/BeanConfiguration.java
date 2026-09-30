package com.github.im2back.hexagonal_architecture_demo.configuration;

import com.github.im2back.hexagonal_architecture_demo.core.application.port.in.CreateOrderUseCase;
import com.github.im2back.hexagonal_architecture_demo.core.application.port.out.OrderRepositoryPort;
import com.github.im2back.hexagonal_architecture_demo.core.application.service.CreateOrderService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public CreateOrderUseCase createOrderUseCase(@Qualifier("orderRepositoryAdapter") OrderRepositoryPort orderRepositoryPort) {
        return new CreateOrderService(orderRepositoryPort);
    }
}