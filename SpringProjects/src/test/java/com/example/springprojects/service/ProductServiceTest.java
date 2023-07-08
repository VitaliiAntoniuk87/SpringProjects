package com.example.springprojects.service;

import com.example.springprojects.dto.ProductDTO;
import com.example.springprojects.entity.Product;
import com.example.springprojects.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

class ProductServiceTest {

    private ProductRepository productRepository;
    private ProductService productService;

    @BeforeEach
    public void init() {
        productRepository = Mockito.mock(ProductRepository.class);
        productService = new ProductService(productRepository);
    }

    @Test
    void addProductsToOrderTest() {
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

        int orderId = 1;

        List<Product> products = productsDTO.stream()
                .map(p -> Product.builder().name(p.getName()).cost(p.getCost())
                        .orderId(orderId).build()).toList();

        when(productRepository.saveAll(products)).thenReturn(products.stream()
                .map(p -> Product.builder()
                        .id(productsDB.size() + 1).name(p.getName()).cost(p.getCost())
                        .orderId(p.getOrderId()).build()).peek(productsDB::add).toList());

        List<ProductDTO> addedProductsToOrder = productService.addProductsToOrder(orderId, productsDTO);

        Assertions.assertEquals(11, addedProductsToOrder.stream()
                .mapToDouble(ProductDTO::getId).sum());
        Assertions.assertEquals(195, productsDB.stream()
                .filter(p -> p.getOrderId() == orderId).mapToDouble(Product::getCost).sum());

    }
}