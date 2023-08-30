package com.bitconex.ordermanagement.administration.uiclient;

import com.bitconex.ordermanagement.administration.controller.ProductController;
import com.bitconex.ordermanagement.administration.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

@Component
public class ProductUIHelper {

    private final ProductController productController;

    @Autowired
    public ProductUIHelper(ProductController productController) {
        this.productController = productController;
    }


    public void showProductCatalogModule() {
        System.out.println("Select option: \n ");
        System.out.println("------------------------------------------");

        System.out.println("1. Entry of a new product into the catalog");
        System.out.println("2. List of all products in the catalog");
        System.out.println("3. Deleting an existing product");
    }

    public void entryIntoProductCatalogModule() {
        Scanner sc = new Scanner(System.in);
        showProductCatalogModule();

        int productEntryOption = sc.nextInt();

        switch (productEntryOption) {
            case 1:
                addProduct();
                break;
            case 2:
                getAllProducts();
                break;
            case 3:
                deleteProduct();
                break;
            default:
                System.out.println("Wrong choice. Select 1, 2 or 3.");
        }
    }


    public void addProduct() {
        String productName;
        double price;
        LocalDate releaseDate;
        LocalDate expirationDate;
        Long availableProductInstances;

        try {
            productName = insertProductName();
            price = insertPrice();
            releaseDate = insertReleaseDate();
            expirationDate = insertExpirationDate();
            availableProductInstances = insertAvailableProductInstances();

            Product product = new Product();
            product.setProductName(productName);
            product.setPrice(price);
            product.setReleaseDate(releaseDate);
            product.setExpirationDate(expirationDate);
            product.setAvailableProductInstances(availableProductInstances);

            productController.addNewProduct(product);
            System.out.println("New product added successfully!");
            System.out.println("Actual list of products: ");
            System.out.println("---------------------");
            getAllProducts();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String insertProductName() {
        Scanner sc = new Scanner(System.in);
        String productName = null;

        while (productName == null || productName.trim().isEmpty()) {
            System.out.println("Enter product Name: ");
            productName = sc.nextLine();

            if (productName.length() < 2) {
                System.out.println("Product name is to short");
                productName = null;
            }
        }
        return productName;
    }

    private double insertPrice() {
        Scanner sc = new Scanner(System.in);
        double price = 0;

        while (price == 0) {
            System.out.println("Enter product price: ");

            if (sc.hasNextDouble()) {
                price = sc.nextDouble();
            } else {
                System.out.println("Invalid input. Try again");
                sc.nextLine();
                price = 0;
            }
        }
        return price;
    }


    private LocalDate insertReleaseDate() {
        System.out.println("Enter release date of the product in format (yyyy-MM-dd): ");
        return dateInputValidation();
    }

    private LocalDate insertExpirationDate() {
        System.out.println("Enter expiration date of the product in format (yyyy-MM-dd): ");
        return dateInputValidation();

    }

    private LocalDate dateInputValidation() {
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate = LocalDate.now();
        LocalDate enteredDate;

        while (true) {
            String dateString = sc.nextLine();

            if (dateString.trim().isEmpty()) {
                System.out.println("You did not enter a date. Try again.");
                continue;
            }

            try {
                enteredDate = LocalDate.parse(dateString, dateTimeFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Incorrect date format. Try again" + e.getMessage());
                continue;
            }

            if (enteredDate.isBefore(currentDate)) {
                System.out.println("The entered date is in the past. Try again.");
                continue;
            }
            break;
        }
        return enteredDate;
    }

    private Long insertAvailableProductInstances() {
        Scanner sc = new Scanner(System.in);
        Long availableProductInstances;
        do {
            System.out.println("Enter product instances: ");

            if (sc.hasNextLong()) {
                availableProductInstances = sc.nextLong();

                if (availableProductInstances == 0) {
                    System.out.println("A number of product instances should not be 0");
                    availableProductInstances = null;
                }
            } else {
                System.out.println("Invalid input format. Enter a valid number.");
                sc.nextLine(); //Clean ivalid input data from the scanner
                availableProductInstances = null;
            }

        } while (availableProductInstances == null);

        return availableProductInstances;
    }


    public void getAllProducts() {
        List<Product> products = productController.getAllProducts();
        if (products != null) {
            for (Product product : products) {
                System.out.println();
                System.out.println(product);
            }
        }
    }

    public void deleteProduct() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter Product Id to be deleted: ");
        Long productId = sc.nextLong();
        productController.deleteProduct(productId);
        System.out.println("Product deleted successfully!");
        System.out.println("List of remaining products: ");
        System.out.println("----------------------------");
        getAllProducts();

    }
}
