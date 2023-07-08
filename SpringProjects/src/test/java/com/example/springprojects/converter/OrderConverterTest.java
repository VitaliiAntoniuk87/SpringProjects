package com.example.springprojects.converter;

import com.example.springprojects.dto.OrderDTO;
import com.example.springprojects.entity.Order;
import com.example.springprojects.entity.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


class OrderConverterTest {

    @Test
    void toOrderDtoTest() {
        int orderId = 1;
        List<Product> products = List.of(
                Product.builder().id(1).name("bread").cost(15).orderId(orderId).build(),
                Product.builder().id(2).name("butter").cost(25).orderId(orderId).build(),
                Product.builder().id(3).name("milk").cost(20).orderId(orderId).build()
        );

        Order order = Order.builder().id(orderId).date(Date.valueOf(LocalDate.now())).build();

        OrderDTO orderDTO = OrderConverter.toOrderDto(order, products);

        Assertions.assertEquals(60, orderDTO.getSum());

    }
}