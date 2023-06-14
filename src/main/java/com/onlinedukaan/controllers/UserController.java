package com.onlinedukaan.controllers;

import com.onlinedukaan.model.User;
import com.onlinedukaan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Controller
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @GetMapping("/signin")
    public String signin(Model model) {
        return "signin";
    }
    @GetMapping("/signin/error?true")
    public String errorSignIn(@PathVariable Boolean param, Model model)
    {
        model.addAttribute("param",param);
        return "signin";
    }

    @PostMapping("/register")
    public String postAddUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
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
        return "customeradded";
    }

    @GetMapping("/signed")
    public String postSignin() {
        return "welcome";
    }
}