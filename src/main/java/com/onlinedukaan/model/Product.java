package com.onlinedukaan.model;

import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Proxy(lazy = false)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long productId;

    @NotBlank
    private String productName;

    @NotNull
    private int price;

    @NotBlank
    private String category;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "cart_id" , nullable = false)
    private Cart cart;


}
