package com.example.springprojects.service;

import com.example.springprojects.dto.ProductDTO;
import com.example.springprojects.entity.Product;
import com.example.springprojects.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO> addProductsToOrder(int orderId, List<ProductDTO> productsDTO) {
        List<Product> products = productsDTO.stream()
                .map(p -> Product.builder().name(p.getName()).cost(p.getCost())
                        .orderId(orderId).build()).toList();
        List<Product> savedProducts = (List<Product>) productRepository.saveAll(products);

        return productsDTO = savedProducts.stream()
                .map(sP -> ProductDTO.builder()
                        .id(sP.getId()).name(sP.getName()).cost(sP.getCost()).build()).toList();
    }


}
