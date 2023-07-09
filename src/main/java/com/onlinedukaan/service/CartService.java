package com.onlinedukaan.service;

import com.onlinedukaan.model.CartItem;
import com.onlinedukaan.model.Product;
import com.onlinedukaan.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    CartItemRepository cartItemRepository;

    public List<Product> findProductById(long userId) {
        List<Product> productList = new ArrayList<>();
        List<CartItem> cartItems = cartItemRepository.findByUser_UserId(userId);
        for (CartItem cartItem : cartItems)
        {
            productList.add(cartItem.getProduct());
        }
        return productList;
    }
}
