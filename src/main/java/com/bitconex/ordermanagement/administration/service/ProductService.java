package com.bitconex.ordermanagement.administration.service;

import com.bitconex.ordermanagement.administration.entity.Product;

import java.util.List;

public interface ProductService {


    void addProduct(Product product);
    List<Product> getAllProducts();
    void deleteProductById(Long productId);
}
