package com.onlinedukaan.service;

import com.onlinedukaan.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    CartItemRepository cartItemRepository;

}
