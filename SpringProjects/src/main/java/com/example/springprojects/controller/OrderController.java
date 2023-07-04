package com.example.springprojects.controller;

import com.example.springprojects.dto.OrderDTO;
import com.example.springprojects.dto.ProductDTO;
import com.example.springprojects.service.OrderService;
import com.example.springprojects.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ProductService productService;


    @GetMapping
    public List<OrderDTO> getAll() {
        return orderService.getAll();
    }

    @GetMapping("/{id}")
    public OrderDTO getOrder(@PathVariable Integer id) {
        return orderService.getOrderById(id);
    }

    @PostMapping
    public OrderDTO createOrder(@RequestBody OrderDTO orderDTO) {
        return orderService.save(orderDTO);
    }

    @PostMapping("/{id}/products")
    public List<ProductDTO> addProductToOrder(@PathVariable Integer id, @RequestBody List<ProductDTO> products) {
        return productService.addProductsToOrder(id, products);
    }
}
