package com.bitconex.ordermanagement.administration.service;

import com.bitconex.ordermanagement.administration.entity.Product;
import com.bitconex.ordermanagement.administration.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProductServiceImpl implements ProductService {


    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public void addProduct(Product product) {
        validateProduct(product);
        productRepository.save(product);
    }

    public void validateProduct(Product p) {
        Product product = productRepository.findProductByProductName(p.getProductName());
        if (product != null) {
            throw new IllegalStateException("Product with Product Name " + p.getProductName() + " already exsists!");
        }

    }
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public void deleteProductById(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            productRepository.deleteById(productId);
        } else {
            throw new IllegalStateException("Product with product id " + productId + " does not exsist!");
        }
    }
}
