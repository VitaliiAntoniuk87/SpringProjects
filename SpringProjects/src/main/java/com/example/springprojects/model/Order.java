package com.example.springprojects.model;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private int id;
    private LocalDate date;
    private double cost;
    private List<Product> products;

    public int getId() {
        return id;
    }


    public double getCost() {
        return cost;
    }


    public List<Product> getProducts() {
        return products;
    }


}
