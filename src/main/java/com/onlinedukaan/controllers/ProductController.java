package com.onlinedukaan.controllers;

import com.onlinedukaan.model.Product;
import com.onlinedukaan.repo.ProductRepo;
import com.onlinedukaan.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class ProductController {
    public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/images/productImages";
    @Autowired
    ProductRepo productRepo;

    @Autowired
    ProductService productService;

    @GetMapping("/products")
    public String getProduct(Model model)
    {
        List<Product> productList = productService.getAllProduct();
        model.addAttribute("products",productList);
        return "products";
    }
    @GetMapping("/products/add")
    public String addProduct(Model model)
    {
        model.addAttribute("product",new Product());
        return "addproduct";
    }
   @PostMapping("/products/add")
    public String addProduct(@ModelAttribute("product")Product product, @RequestParam("image") MultipartFile multipartFile)throws IOException
   {
       if(!multipartFile.isEmpty())
       {
           String fileName = multipartFile.getOriginalFilename();
           Path fileNameAndPath = Paths.get(uploadDir,fileName);
           Files.write(fileNameAndPath,multipartFile.getBytes());
       }
         Product savedProduct = productRepo.save(product);
       return "/index";
   }
}
