package com.byteworks.service;

import com.byteworks.dto.OrderDto;
import com.byteworks.model.Order;

import java.util.List;

public interface OrderService {
    
    Order findById(Long id);
    
    Order save(Order order);

    String validateOrder(OrderDto orderDto);

    Order processOrder(OrderDto orderDto);

    List<Order> findAllOrders();
}
