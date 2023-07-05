package com.onlinedukaan.model;

import lombok.Data;

import javax.persistence.*;



@Entity
@Data
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cart_id")
    private int cartId;

    @OneToOne(mappedBy = "cart")
    private User user;

}
