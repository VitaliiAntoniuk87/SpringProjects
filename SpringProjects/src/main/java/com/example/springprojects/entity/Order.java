package com.example.springprojects.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.util.Date;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("orders")
public class Order {

    @Id
    private int id;
    @Column("create_date")
    private Date date;

}
