package com.example.springprojects.service;

import com.example.springprojects.dto.OrderDTO;
import com.example.springprojects.dto.ProductDTO;
import com.example.springprojects.entity.Order;
import com.example.springprojects.entity.Product;
import com.example.springprojects.repository.OrderRepository;
import com.example.springprojects.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;


class OrderServiceTest {

    private OrderService orderService;
    private OrderRepository orderRepository;
    private ProductRepository productRepository;

    @BeforeEach
    public void init() {
        this.orderRepository = Mockito.mock(OrderRepository.class);
        this.productRepository = Mockito.mock(ProductRepository.class);
        this.orderService = new OrderService(orderRepository, productRepository);
    }

    @Test
    void getOrderByIdTest() {
        int orderId = 13;
        List<Product> products = List.of(
                Product.builder().name("bread").cost(15).orderId(orderId).build(),
                Product.builder().name("butter").cost(25).orderId(orderId).build(),
                Product.builder().name("milk").cost(20).orderId(orderId).build()
        );

        Order order = Order.builder().id(orderId).date(Date.valueOf(LocalDate.now())).build();

        when(orderRepository.findById(anyInt())).thenReturn(Optional.of(order));
        when(productRepository.findAllByOrderId(anyInt())).thenReturn(products);

        OrderDTO orderDTO = orderService.getOrderById(orderId);

        Assertions.assertEquals(60, orderDTO.getSum());
    }


    @Test
    void getAllTest() {
        int orderId1 = 5;
        int orderId2 = 7;
        List<Product> products = List.of(
                Product.builder().id(1).name("bread").cost(15).orderId(orderId1).build(),
                Product.builder().id(2).name("butter").cost(25).orderId(orderId1).build(),
                Product.builder().id(3).name("milk").cost(20).orderId(orderId1).build(),
                Product.builder().id(4).name("water").cost(5).orderId(orderId2).build(),
                Product.builder().id(5).name("soda").cost(20).orderId(orderId2).build()
        );

        List<Order> orders = List.of(
                Order.builder().id(orderId1).date(Date.valueOf(LocalDate.now())).build(),
                Order.builder().id(orderId2).date(Date.valueOf(LocalDate.now())).build()
        );

        when(orderRepository.findAll()).thenReturn(orders);
        when(productRepository.findAllByOrderId(orderId1)).thenReturn(products.stream()
                .filter(p -> p.getOrderId() == orderId1).toList());
        when(productRepository.findAllByOrderId(orderId2)).thenReturn(products.stream()
                .filter(p -> p.getOrderId() == orderId2).toList());

        List<OrderDTO> orderDTOS = orderService.getAll();

        Assertions.assertEquals(2, orderDTOS.size());
        Assertions.assertEquals(60, orderDTOS.stream()
                .filter(o -> o.getId() == orderId1).mapToDouble(OrderDTO::getSum).sum());

    }


    @Test
    void saveTest() {
        List<ProductDTO> productsDTO = List.of(
                ProductDTO.builder().name("sweet").cost(65).build(),
                ProductDTO.builder().name("fish").cost(90).build()
        );

        List<Product> productsDB = new ArrayList<>() {
            {
                add(Product.builder().id(1).name("bread").cost(15).orderId(1).build());
                add(Product.builder().id(2).name("butter").cost(25).orderId(1).build());
                add(Product.builder().id(3).name("milk").cost(20).orderId(2).build());
                add(Product.builder().id(4).name("water").cost(5).orderId(2).build());

            }
        };


        double orderSum = productsDTO.stream().mapToDouble(ProductDTO::getCost).sum();

        OrderDTO orderDTO = OrderDTO.builder()
                .date(Date.valueOf(LocalDate.now())).products(productsDTO).sum(orderSum).build();

        Order order = Order.builder().date(orderDTO.getDate()).build();

        int newOrderId = 4;

        when(orderRepository.save(order)).thenReturn(Order.builder()
                .id(newOrderId).date(order.getDate()).build());



        List<Product> products = orderDTO.getProducts().stream()
                .map(p -> Product.builder().name(p.getName()).cost(p.getCost())
                        .orderId(newOrderId).build()).toList();


        when(productRepository.saveAll(products)).thenReturn(products.stream()
                .map(p -> Product.builder()
                        .id(productsDB.size() + 1).name(p.getName()).cost(p.getCost())
                        .orderId(p.getOrderId()).build()).peek(productsDB::add).toList());

        OrderDTO orderDTO1 = orderService.save(orderDTO);


        Assertions.assertEquals(6,productsDB.size());
        Assertions.assertEquals(90,productsDB.get(5).getCost());
        Assertions.assertEquals(5,productsDB.get(4).getId());
        Assertions.assertEquals(155,orderDTO1.getSum());
    }
}