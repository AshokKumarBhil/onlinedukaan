package com.onlinedukaan.service;

import com.onlinedukaan.model.Product;
import com.onlinedukaan.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    List<Product> productList;

    public List<Product> getAllProduct() {
        productList = productRepository.findAll();
        return productList;
    }

    public List<Product> getGrocery() {
        return productList;
    }

    public List<Product> getStationary() {
        return productList;
    }

    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }

    public void updateProduct(long id) {

    }

    public Product getProduct(long id) {
        return productRepository.getById(id);
    }

    public List<Product> getGroceryProducts()
    {
        return productRepository.getGroceryProducts();
    }

    public List<Product> getStationaryProducts()
    {
        return productRepository.getStationaryProducts();
    }
}
