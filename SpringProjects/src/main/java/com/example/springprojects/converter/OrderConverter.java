package com.example.springprojects.converter;

import com.example.springprojects.dto.OrderDTO;
import com.example.springprojects.dto.ProductDTO;
import com.example.springprojects.entity.Order;
import com.example.springprojects.entity.Product;

import java.util.List;

public class OrderConverter {

    public static OrderDTO toOrderDto(Order order, List<Product> products) {
        double orderSum = products.stream().mapToDouble(Product::getCost).sum();

        List<ProductDTO> productsDTO = products.stream().map(p -> ProductDTO.builder()
                .id(p.getId()).name(p.getName()).cost(p.getCost()).build()).toList();

        return OrderDTO.builder()
                .id(order.getId()).date(order.getDate()).products(productsDTO).sum(orderSum).build();
    }
}
