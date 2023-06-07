package com.onlinedukaan.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "products")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long productId;
    @NotBlank
    private String productName;
    @NotBlank
    private float price;
    @NotBlank
    private String category;

    private String pictureUrl;
    private float weight;

}
