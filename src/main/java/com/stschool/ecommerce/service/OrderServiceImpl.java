package com.stschool.ecommerce.service;

import com.stschool.ecommerce.dto.OrderDto;
import com.stschool.ecommerce.entity.Order;
import com.stschool.ecommerce.enums.OrderStatus;
import com.stschool.ecommerce.exception.OrderExistsException;
import com.stschool.ecommerce.exception.OrderNotFoundException;
import com.stschool.ecommerce.repository.OrderRepository;
import com.stschool.ecommerce.util.IdGeneratorUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Override
    public OrderDto save(Order order) throws OrderExistsException {
        try {
            order.setOrderId(IdGeneratorUtil.generateOrderId());

            order.setOrderedDate(LocalDateTime.now());
            /*
                Setting Default Status
             */
            order.setOrderStatus(OrderStatus.PLACED);

            order.setExpectedDeliveryDate(
                    order.getOrderedDate().plusDays(7)
            );

            Order savedOrder = orderRepository.save(order);

            return modelMapper.map(savedOrder, OrderDto.class);

        } catch (DataIntegrityViolationException ex) {
            throw new OrderExistsException("Duplicate Order Id generated");
        }
    }

    @Override
    public List<OrderDto> getAll(int page, int size, String sortBy, String direction) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Order> orders = orderRepository.findAllByOrderByOrderedDateDesc(pageable);

        return orders.getContent().stream().map(order -> modelMapper.map(order, OrderDto.class)).toList();
    }

    @Override
    public OrderDto getById(int id) throws OrderNotFoundException {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order with id " + id + " not found"));

        return modelMapper.map(order, OrderDto.class);
    }

    @Override
    public OrderDto update(Order order) throws OrderNotFoundException {
        Order existingOrder = orderRepository.findById(order.getId())
                .orElseThrow(() -> new OrderNotFoundException("Order with id " + order.getId() + " not found"));

        order.setOrderId(existingOrder.getOrderId());

        if (order.getPayment() != null) {
            order.getPayment().setOrder(order);
        }

        Order updatedOrder = orderRepository.save(order);
        return modelMapper.map(updatedOrder, OrderDto.class);
    }

    @Override
    public void deleteById(int id) throws OrderNotFoundException {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order with id " + id + " not found"));
        orderRepository.delete(order);
    }
}
