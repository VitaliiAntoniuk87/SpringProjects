package com.example.springprojects.service;

import com.example.springprojects.converter.OrderConverter;
import com.example.springprojects.dto.OrderDTO;
import com.example.springprojects.entity.Order;
import com.example.springprojects.entity.Product;
import com.example.springprojects.repository.OrderRepository;
import com.example.springprojects.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;


    public List<OrderDTO> getAll() {
        return orderRepository.findAll().stream()
                .map(o -> OrderConverter.toOrderDto(o, productRepository.findAllByOrderId(o.getId()))).toList();
    }

    public OrderDTO getOrderById(int id) {
        return orderRepository.findById(id)
                .map(o -> OrderConverter.toOrderDto(o, productRepository.findAllByOrderId(id)))
                .orElseThrow(IllegalArgumentException::new);
    }

    public OrderDTO save(OrderDTO orderDTO) {
        Order order = Order.builder().date(Date.valueOf(LocalDate.now())).build();
        Order savedOrder = orderRepository.save(order);
        List<Product> products = orderDTO.getProducts().stream()
                .map(p -> Product.builder().name(p.getName()).cost(p.getCost())
                        .orderId(savedOrder.getId()).build()).toList();
        List<Product> savedProducts = (List<Product>) productRepository.saveAll(products);
        return OrderConverter.toOrderDto(savedOrder, savedProducts);
    }

}
