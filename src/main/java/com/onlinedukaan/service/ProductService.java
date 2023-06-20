package com.onlinedukaan.service;

import com.onlinedukaan.model.Product;
import com.onlinedukaan.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepo productRepo;
    List<Product> productList;

    public List<Product> getAllProduct() {
        productList = productRepo.findAll();
        return productList;
    }

    public List<Product> getGrocery() {
        return productList;
    }

    public List<Product> getStationary() {
        return productList;
    }

    public void deleteProduct(long id) {
        productRepo.deleteById(id);
    }

    public void updateProduct(long id) {

    }

    public Product getProduct(long id) {
        return productRepo.getById(id);
    }
}
