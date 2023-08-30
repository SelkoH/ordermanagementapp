package com.bitconex.ordermanagement.administration.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Id;

    @Column(name = "product_name", nullable = false)
    private String productName;
    @Column(name = "price", nullable = false)
    private double price;
    @Column(name = "release_date", nullable = false)
    private LocalDate releaseDate;
    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;
    @Column(name = "product_instances", nullable = false)
    private Long availableProductInstances;

    public Product() {
    }

    public Product(Long id, String productName, double price, LocalDate releaseDate, LocalDate expirationDate, Long availableProductInstances) {
        Id = id;
        this.productName = productName;
        this.price = price;
        this.releaseDate = releaseDate;
        this.expirationDate = expirationDate;
        this.availableProductInstances = availableProductInstances;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Long getAvailableProductInstances() {
        return availableProductInstances;
    }

    public void setAvailableProductInstances(Long availableProductInstances) {
        this.availableProductInstances = availableProductInstances;
    }

    @Override
    public String toString() {
        return "Product: " + '\n' +
                "Id=" + Id +
                ", Product Name='" + productName + '\'' +
                ", Price=" + price +
                ", Release Date=" + releaseDate +
                ", Expiration Date=" + expirationDate +
                ", Available Product Instances=" + availableProductInstances;
    }
}
