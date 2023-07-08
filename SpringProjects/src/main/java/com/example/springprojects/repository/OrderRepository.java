package com.example.springprojects.repository;

import com.example.springprojects.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

    @Override
    <S extends Order> S save(S entity);

    @Override
    List<Order> findAll();

    Optional<Order> findById(int id);
}
