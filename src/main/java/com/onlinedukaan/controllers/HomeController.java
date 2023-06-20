package com.onlinedukaan.controllers;

import com.onlinedukaan.model.Product;
import com.onlinedukaan.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    ProductService productService;
    @GetMapping("/shop")
    public String shop(Model model)
    {
        List<Product> products = productService.getAllProduct();
        model.addAttribute("products",products);
        return "shop";
    }
    @GetMapping("/shop/viewproduct/{id}")
    public String viewProduct(@PathVariable long id,Model model){
        Product product = productService.getProduct(id);
        model.addAttribute("product",product);
        return "viewProduct";
    }
}
