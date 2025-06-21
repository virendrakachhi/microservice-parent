package com.order_service.order_service.repository;

import com.order_service.order_service.dto.OrderLineItemsDto;
import com.order_service.order_service.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{}