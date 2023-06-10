package com.onlinedukaan.controllers;

import com.onlinedukaan.model.Product;
import com.onlinedukaan.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    ProductService product_service;

    @GetMapping("/grocery")
    public String getGrocery(Model model) {
        List<Product> groceryProductList = product_service.getGrocery();
        model.addAttribute("grocerylist", groceryProductList);
        return "groceryPage";
    }

    @GetMapping("/stationary")
    public String getStationary(Model model) {
        List<Product> stationaryProductList = product_service.getStationary();
        model.addAttribute("stationarylist", stationaryProductList);
        return "stationaryPage";
    }
}
