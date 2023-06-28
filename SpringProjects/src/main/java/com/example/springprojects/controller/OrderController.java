package com.example.springprojects.controller;

import com.example.springprojects.model.Order;
import com.example.springprojects.model.Product;
import com.example.springprojects.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> getAll() {
        return orderService.getOrders();
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Integer id) {
        return orderService.getOrderById(id);
    }

    @PostMapping
    public Order createOrder(@RequestBody Product[] products) {
        return orderService.save(products);
    }

    @PostMapping("/{id}")
    public void addProductToOrder(@PathVariable Integer id, @RequestBody Product[] products) {
        orderService.addProductsToOrder(id, products);
    }
}
