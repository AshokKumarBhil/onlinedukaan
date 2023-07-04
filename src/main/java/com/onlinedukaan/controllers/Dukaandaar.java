package com.onlinedukaan.controllers;

import com.onlinedukaan.global.GlobalData;
import com.onlinedukaan.model.Product;
import com.onlinedukaan.repo.ProductRepo;
import com.onlinedukaan.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class Dukaandaar {

    public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/images";

    @Autowired
    ProductRepo productRepo;

    @Autowired
    ProductService productService;

    @GetMapping("/dukaandaar")
    public String admin() {
        return "adminpage";
    }

    @GetMapping("dukaandaar/products")
    public String getProduct(Model model, @PathParam(value = "category") String category) {
        List<Product> productList;
        if (category != null && category.equals("stationary")) {
            productList = productService.getStationaryProducts();
            model.addAttribute("products", productList);
            return "products";
        }
        if (category != null && category.equals("grocery")) {
            productList = productService.getGroceryProducts();
            model.addAttribute("products", productList);
            return "products";
        }

        productList = productService.getAllProduct();
        model.addAttribute("products", productList);
        return "products";
    }

    @GetMapping("dukaandaar/products/add")
    public String addProduct(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("cart", GlobalData.cart);
        return "addproduct";
    }

    @PostMapping("dukaandaar/products/add")
    public String addProduct(@ModelAttribute("product") Product product, @RequestParam("image") MultipartFile multipartFile, @RequestParam("imgName") String imgUrl) throws IOException {
        String fileName = new String();
        if (!multipartFile.isEmpty()) {
            fileName = multipartFile.getOriginalFilename();
            Path fileNameAndPath = Paths.get(uploadDir, fileName);
            Files.write(fileNameAndPath, multipartFile.getBytes());
            product.setImageUrl(fileName);
            productRepo.save(product);
            return "redirect:/getProducts";
        }
        product.setImageUrl(imgUrl);
        productRepo.save(product);
        return "redirect:/getProducts";
    }

    @GetMapping("dukaandaar/products/delete/{id}")
    public String deleteProduct(@PathVariable long id) {
        productService.deleteProduct(id);
        return "redirect:/getProducts";
    }

    @GetMapping("dukaandaar/products/update/{id}")
    public String updateProduct(@PathVariable long id, Model model) {
        Product product = productService.getProduct(id);
        model.addAttribute("product", product);
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "addproduct";
    }

}
