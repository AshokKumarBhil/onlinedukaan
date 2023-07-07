package com.onlinedukaan.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@Entity
@Setter
@Getter
@Proxy(lazy = false)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    long productId;

    @NotBlank
    private String productName;

    @NotNull
    private int price;

    @NotBlank
    private String category;

    private String imageUrl;


}
