package com.onlinedukaan.controllers;

import com.onlinedukaan.global.GlobalData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CheckoutController {
    @GetMapping("/checkout")
    public String checkout(Model model){
        model.addAttribute("total",GlobalData.totalPrice());
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "checkout";
    }
}
