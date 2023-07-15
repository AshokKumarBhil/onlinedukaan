package com.onlinedukaan.service;

import com.onlinedukaan.model.CartItem;
import com.onlinedukaan.model.Product;
import com.onlinedukaan.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartService {

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    ProductService productService;

    public List<Product> findProductById(long userId) {
        List<Product> productList = new ArrayList<>();
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        for (CartItem cartItem : cartItems)
        {
            productList.add(cartItem.getProduct());
        }
        return productList;
    }

    public Map<Long, Integer> findQuantityOfProduct(long userId) {
        Map<Long,Integer> map = new HashMap<>();
        List<Product> productList = new ArrayList<>();
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        for (CartItem cartItem : cartItems)
        {
            map.put(cartItem.getProduct().getProductId(),cartItem.getQuantity());
        }
        return map;
    }

    public int getTotalPriceOfItems(Map<Long, Integer> productQuantityMap) {
        int totalPrice = 0;
        for (Long key : productQuantityMap.keySet()) {
            int productPrice = productService.getProduct(key).getPrice();
             totalPrice = totalPrice + productQuantityMap.get(key) * productPrice;
        }
        return totalPrice;
    }
}
