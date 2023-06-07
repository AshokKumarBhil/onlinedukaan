package com.onlinedukaan.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String order_id;
    private int customer_id;
    private String Product_id;
}
