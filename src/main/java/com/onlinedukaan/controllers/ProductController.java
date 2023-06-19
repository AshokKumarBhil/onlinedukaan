package com.onlinedukaan.controllers;

import com.onlinedukaan.model.Product;
import com.onlinedukaan.repo.ProductRepo;
import com.onlinedukaan.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class ProductController {
    public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/images";
    @Autowired
    ProductRepo productRepo;

    @Autowired
    ProductService productService;

    @GetMapping("/getProducts")
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
       String fileName = new String();
       if(!multipartFile.isEmpty())
       {
           fileName = multipartFile.getOriginalFilename();
           Path fileNameAndPath = Paths.get(uploadDir,fileName);
           Files.write(fileNameAndPath,multipartFile.getBytes());
       }
         product.setImageUrl(fileName);
         Product savedProduct = productRepo.save(product);
       return "/index";
   }
   @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable long id){
      productService.deleteProduct(id);
      return "redirect:/getProducts";
   }

    @GetMapping("/products/update/{id}")
    public String updateProduct(@PathVariable long id , Model model){
        Product product = productService.getProduct(id);
        model.addAttribute("product",product);
        return "addproduct";
    }

}
