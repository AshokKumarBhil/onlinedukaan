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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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


    @PostMapping("/addtocart/{id}")
    public String addToCart(@ModelAttribute(name = "cartItem") CartItem cartItem ,@PathVariable(name = "id") long productId, Principal principal) {
        String email = principal.getName();
        User user = userService.findUserByEmail(email);
        Product product = productService.getProduct(productId);
        List<CartItem> itemList = new ArrayList<>();

        if (cartItemRepository.existsByUser_UserIdAndProduct_ProductId(user.getUserId(), productId)) {
            CartItem exitingCartItem = cartItemRepository.findByUserAndProduct(user,product);
            int newQuantity =  cartItem.getQuantity();
            exitingCartItem.setQuantity(newQuantity);
            cartItemRepository.save(exitingCartItem);

            return "redirect:/shop";
        }
        cartItem.setProduct(product);
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
        Map<Long,Integer> productQuantityMap = cartService.findQuantityOfProduct(user.getUserId());
        int totalPrice = cartService.getTotalPriceOfItems(productQuantityMap);
        int cartCount = cart.size();
        model.addAttribute("cartCount",cartCount);
        model.addAttribute("cart", cart);
        model.addAttribute("map",productQuantityMap);
        model.addAttribute("total",totalPrice);
        return "cart";
    }

    @GetMapping("/cart/removeitem/{id}")
    public String removeItem(@PathVariable int id, Model model,Principal principal) {
        String email = principal.getName();
        User user = userService.findUserByEmail(email);
        Product product = productService.getProduct(id);
        cartItemRepository.deleteByProductAndUser(product,user);
        return "redirect:/cart";
    }
    @GetMapping("/cart/updatequantity/{id}")
    public String updateQuantity(@PathVariable long id,Model model,Principal principal){
        String email = principal.getName();
        User user = userService.findUserByEmail(email);
        Product product = productService.getProduct(id);
        CartItem cartItem = cartItemRepository.findByUserAndProduct(user,product);
        model.addAttribute("cartItem",cartItem);
        model.addAttribute("product",product);
        return "viewproduct";
    }
}
