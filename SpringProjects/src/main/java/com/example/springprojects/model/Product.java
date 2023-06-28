package com.example.springprojects.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private int id;
    private String name;
    private double cost;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }
}
