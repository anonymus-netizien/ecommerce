package com.stschool.ecommerce.service;

import com.stschool.ecommerce.dto.OrderDto;
import com.stschool.ecommerce.entity.Order;
import com.stschool.ecommerce.exception.OrderExistsException;
import com.stschool.ecommerce.exception.OrderNotFoundException;

import java.util.List;

public interface OrderService {

    OrderDto save(Order order) throws OrderExistsException;

    List<OrderDto> getAll(int page, int size, String sortBy, String direction);

    OrderDto getById(int id) throws OrderNotFoundException;

    OrderDto update(Order order) throws OrderNotFoundException;

    void deleteById(int id) throws OrderNotFoundException;
}
