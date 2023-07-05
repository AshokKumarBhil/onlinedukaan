package com.onlinedukaan.controllers;

import com.onlinedukaan.global.GlobalData;
import com.onlinedukaan.model.Product;
import com.onlinedukaan.service.ProductService;
import com.onlinedukaan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CartController {

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @GetMapping("/addtocart/{id}")
    public String addToCart(@PathVariable long id, Model model) {

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
