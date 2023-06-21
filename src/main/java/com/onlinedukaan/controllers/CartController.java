package com.onlinedukaan.controllers;

import com.onlinedukaan.global.GlobalData;
import com.onlinedukaan.model.Product;
import com.onlinedukaan.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CartController {

    @Autowired
    ProductService productService;

    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable long id,Model model) {
        GlobalData.cart.add(productService.getProduct(id));
        return "redirect:/shop";
    }

    @GetMapping("/cart")
    public String getCart(Model model) {
        int cartCount = GlobalData.cart.size();
        int total = 0;
        for (Product product : GlobalData.cart) {
            total = total + product.getPrice();
        }
        model.addAttribute("total", total);
        model.addAttribute("cartCount", cartCount);
        model.addAttribute("cart", GlobalData.cart);
        return "cart";
    }
}
