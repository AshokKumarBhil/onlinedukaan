package com.onlinedukaan.controllers;

import com.onlinedukaan.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Dukaandaar {

    @GetMapping("/dukaandaar")
    public String admin() {
        return "adminpage";
    }
    @GetMapping("/dukaandaar/products")
    public String getProduct()
    {
        return "products";
    }
    @GetMapping("/dukaandaar/products/add")
    public String addProduct(Model model)
    {
        model.addAttribute("product",new Product());
        return "addproduct";
    }

}
