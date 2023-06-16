package com.onlinedukaan.controllers;

import com.onlinedukaan.model.Product;
import com.onlinedukaan.repo.ProductRepo;
import com.onlinedukaan.util.FileUploadUtil;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Controller
public class ProductController {
    @Autowired
    ProductRepo productRepo;

    @GetMapping("/products")
    public String getProduct()
    {
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
       String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
       product.setImageUrl(fileName);
       System.out.println(product);
       Product savedProduct = productRepo.save(product);
       String uploadDir = "productImages/" + savedProduct.getProductName();
       FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
       return "/index";
   }
}
