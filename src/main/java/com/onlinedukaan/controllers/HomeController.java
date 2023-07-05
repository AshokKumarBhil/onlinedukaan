package com.onlinedukaan.controllers;

import com.onlinedukaan.global.GlobalData;
import com.onlinedukaan.model.Product;
import com.onlinedukaan.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.websocket.server.PathParam;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    ProductService productService;

    @GetMapping("/")
    public String home(Model model) {

        return "index";
    }

    @GetMapping("/shop")
    public String shop(Model model, @PathParam(value = "category")String category) {
        List<Product> products;
        if(category != null && category.equals("stationary"))
        {
            products = productService.getStationaryProducts();
            model.addAttribute("products",products);
            return "shop";
        }
        if(category != null && category.equals("grocery"))
        {
            products = productService.getGroceryProducts();
            model.addAttribute("products",products);
            return "shop";
        }
        products = productService.getAllProduct();
        model.addAttribute("products", products);
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "shop";
    }

    @GetMapping("/shop/viewproduct/{id}")
    public String viewProduct(@PathVariable long id, Model model) {
        Product product = productService.getProduct(id);
        model.addAttribute("product", product);
        return "viewproduct";
    }
}
