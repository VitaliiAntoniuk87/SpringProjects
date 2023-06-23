package com.example.springprojects.service;

import com.example.springprojects.model.Order;
import com.example.springprojects.model.Product;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class OrderService {

    private final List<Order> orders = new ArrayList<>() {
        {
            add(Order.builder().id(1).date(LocalDate.now())
                    .products(new ArrayList<>() {
                                  {
                                      add(Product.builder().id(1).name("Bread").cost(7.25).build());
                                      add(Product.builder().id(2).name("Butter").cost(15.45).build());
                                  }
                              }
                    )
                    .cost(22.7)
                    .build());

            add(Order.builder().id(2).date(LocalDate.now())
                    .products(new ArrayList<>() {
                                  {
                                      add(Product.builder().id(4).name("Milk").cost(9.60).build());
                                      add(Product.builder().id(5).name("Choco").cost(15.45).build());
                                  }
                              }
                    )
                    .cost(25.05)
                    .build());

            add(Order.builder().id(3).date(LocalDate.now())
                    .products(new ArrayList<>() {
                                  {
                                      add(Product.builder().id(3).name("Cola").cost(19.35).build());
                                      add(Product.builder().id(5).name("Choco").cost(15.45).build());
                                      add(Product.builder().id(6).name("Meat").cost(75.35).build());
                                  }
                              }

                    )
                    .cost(110.15)
                    .build());
        }
    };

    public List<Order> getOrders() {
        return orders;
    }

    public Order getOrderById(int id) {
        return orders.stream().filter(e -> e.getId() == id).findFirst().orElseThrow();
    }

    public Order save(Product[] products) {
        Order order = Order.builder().id(orders.size() + 1)
                .date(LocalDate.now())
                .cost(getNewProductSum(products))
                .products(Arrays.asList(products))
                .build();
        orders.add(order);
        return order;
    }

    public void addProductsToOrder(int orderId, Product[] products) {
        orders.stream()
                .filter(o -> o.getId() == orderId)
                .findFirst()
                .ifPresent(o -> {
                    o.setCost(o.getCost() + getNewProductSum(products));
                    o.getProducts().addAll(Arrays.asList(products));
                });
    }


    private static double getNewProductSum(Product[] products) {
        return Arrays.stream(products)
                .mapToDouble(Product::getCost)
                .sum();
    }

}
