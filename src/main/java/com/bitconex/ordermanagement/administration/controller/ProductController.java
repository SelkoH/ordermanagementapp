package com.bitconex.ordermanagement.administration.controller;

import com.bitconex.ordermanagement.administration.entity.Product;
import com.bitconex.ordermanagement.administration.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/productCatalog")
public class ProductController {


    private final ProductServiceImpl productService;

    @Autowired
    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @PostMapping(path = "/addNewProduct")
    public void addNewProduct(@RequestBody Product product) {
        productService.addProduct(product);

    }

    @GetMapping(path = "/products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();

    }

    @DeleteMapping(path = "{productId}")
    public void deleteProduct(@PathVariable("productId") Long productId) {
        productService.deleteProductById(productId);
    }


}
