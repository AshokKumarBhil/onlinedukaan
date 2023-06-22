package com.onlinedukaan.controllers;

import com.onlinedukaan.model.User;
import com.onlinedukaan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
@Controller
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @GetMapping("/signin")
    public String signin(@RequestParam(value = "error", defaultValue = "false") boolean loginError, Model model) {
        if (loginError) {
            model.addAttribute("error", loginError);
            return "signin";
        }
        return "signin";
    }

    @PostMapping("/register")
    public String postAddUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model,HttpServletRequest request) throws ServletException {
        User existingUser = userService.findUserByEmail(user.getEmail());
        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "/register";
        }
        userService.addUser(user);
        return "redirect:/";
    }
}