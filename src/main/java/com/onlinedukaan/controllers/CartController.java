package com.onlinedukaan.controllers;

import com.onlinedukaan.model.CartItem;
import com.onlinedukaan.model.Product;
import com.onlinedukaan.model.User;
import com.onlinedukaan.repository.CartItemRepository;
import com.onlinedukaan.repository.UserRepository;
import com.onlinedukaan.service.ProductService;
import com.onlinedukaan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/addtocart/{id}")
    public String addToCart(@PathVariable long id, Model model , Principal principal) {
        String email = principal.getName();
        User user = userService.findUserByEmail(email);
        CartItem cartItem = new CartItem();
        Product product = productService.getProduct(id);
        cartItem.setProduct(product);
        cartItem.setUser(user);
        List<CartItem> itemList = new ArrayList<>();
        itemList.add(cartItem);
        user.setCartItems(itemList);
        cartItemRepository.save(cartItem);
        return "redirect:/shop";
    }

    @GetMapping("/viewcart")
    public String getCart(Model model) {

        return "cart";
    }

    @GetMapping("/cart/removeproduct/{id}")
    public String removeItem(@PathVariable int id, Model model) {

        return "redirect:/viewcart";
    }
}
