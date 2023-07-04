package com.onlinedukaan.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "carts")
@Data
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cart_id")
    private int id;

    @OneToOne(mappedBy = "cart")
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.REMOVE)
    private List<Product> productList;
}
