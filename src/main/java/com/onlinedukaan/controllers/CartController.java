package com.onlinedukaan.controllers;

import com.onlinedukaan.model.CartItem;
import com.onlinedukaan.model.Product;
import com.onlinedukaan.model.User;
import com.onlinedukaan.repository.CartItemRepository;
import com.onlinedukaan.service.CartService;
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
    CartService cartService;

    @Autowired
    CartItemRepository cartItemRepository;


    @GetMapping("/addtocart/{id}")
    public String addToCart(@PathVariable long id, Model model, Principal principal) {
        String email = principal.getName();
        User user = userService.findUserByEmail(email);
        Product product = productService.getProduct(id);
        if (cartItemRepository.existsByUser_UserIdAndProduct_ProductId(user.getUserId(), id)) {
            System.out.println("Product Exits for This User");
            CartItem existingCartItem = cartItemRepository.findByUser_UserIdAndProduct_ProductId(user.getUserId(), id).get();
            int quantity = existingCartItem.getQuantity() + 1;
            existingCartItem.setQuantity(quantity);
            cartItemRepository.updateQuantityById(existingCartItem.getId(),quantity);
            return "redirect:/shop";
        }
        List<CartItem> itemList = new ArrayList<>();
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(1);
        cartItem.setUser(user);
        itemList.add(cartItem);
        user.setCartItems(itemList);
        cartItemRepository.save(cartItem);
        return "redirect:/shop";


    }

    @GetMapping("/cart")
    public String getCart(Model model, Principal principal) {
        String email = principal.getName();
        User user = userService.findUserByEmail(email);
        List<Product> cart = cartService.findProductById(user.getUserId());
        model.addAttribute("cart", cart);
        return "cart";
    }

    @GetMapping("/cart/removeproduct/{id}")
    public String removeItem(@PathVariable int id, Model model) {

        return "redirect:/viewcart";
    }
}
