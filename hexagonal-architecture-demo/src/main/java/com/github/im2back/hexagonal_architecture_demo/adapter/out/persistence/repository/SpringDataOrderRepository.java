package com.github.im2back.hexagonal_architecture_demo.adapter.out.persistence.repository;


import com.github.im2back.hexagonal_architecture_demo.adapter.out.persistence.entity.OrderJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataOrderRepository extends JpaRepository<OrderJpaEntity, Long> {
}