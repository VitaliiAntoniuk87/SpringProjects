package com.example.springprojects.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class OrderDTO {

    private int id;
    private Date date;
    private List<ProductDTO> products;
    private double sum;
}
