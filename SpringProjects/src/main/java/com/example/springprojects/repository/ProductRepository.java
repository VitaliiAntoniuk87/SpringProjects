package com.example.springprojects.repository;

import com.example.springprojects.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

//    List<Product> saveAll(List<Product> products);

    @Override
    <S extends Product> Iterable<S> saveAll(Iterable<S> entities);

    List<Product> findAllByOrderId(int orderId);

    @Override
    List<Product> findAll();
}
