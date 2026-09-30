package com.github.im2back.hexagonal_architecture_demo.adapter.out.persistence.jpa;

import com.github.im2back.hexagonal_architecture_demo.adapter.out.persistence.jpa.entity.OrderJpaEntity;
import com.github.im2back.hexagonal_architecture_demo.adapter.out.persistence.jpa.mapper.OrderPersistenceMapper;
import com.github.im2back.hexagonal_architecture_demo.adapter.out.persistence.jpa.repository.SpringDataOrderRepository;
import com.github.im2back.hexagonal_architecture_demo.core.application.port.out.OrderRepositoryPort;
import com.github.im2back.hexagonal_architecture_demo.core.domain.order.model.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderRepositoryAdapter implements OrderRepositoryPort {

    private final SpringDataOrderRepository repository;

    public OrderRepositoryAdapter(
            SpringDataOrderRepository repository
    ) {
        this.repository = repository;
    }

    @Override
    public Order save(Order order) {

        OrderJpaEntity entity =
                OrderPersistenceMapper.toEntity(order);

        OrderJpaEntity savedEntity =
                repository.save(entity);

        return OrderPersistenceMapper.toDomain(savedEntity);
    }
}