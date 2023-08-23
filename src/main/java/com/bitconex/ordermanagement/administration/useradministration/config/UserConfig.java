package com.bitconex.ordermanagement.administration.useradministration.config;

import com.bitconex.ordermanagement.administration.useradministration.controller.UserController;
import com.bitconex.ordermanagement.administration.useradministration.dto.UserDTO;
import com.bitconex.ordermanagement.administration.useradministration.entity.*;
import com.bitconex.ordermanagement.administration.useradministration.repository.RoleRepository;
import com.bitconex.ordermanagement.administration.useradministration.repository.UserRepository;
import com.bitconex.ordermanagement.administration.useradministration.service.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.util.Pair;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;


@Configuration
public class UserConfig {

    @Autowired
    private UserController userController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private RoleRepository roleRepository;
    private Set<Role> roles;


    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {

            Scanner sc = new Scanner(System.in);
            createInitalUsersAndRoles();
            int selection;

            Boolean hasAdminRole;
            Boolean hasBuyerRole;

            User loggedUser = login();
            Pair<Boolean, Boolean> hasRoles = getUserRoles(loggedUser);
            hasAdminRole = hasRoles.getFirst();
            hasBuyerRole = hasRoles.getSecond();


            if (hasAdminRole) {


                showAdministrationModule();
                selection = sc.nextInt();

                switch (selection) {
                    case 1:

                        showUserAdministrationModule();

                        int userEntryOption = sc.nextInt();

                        switch (userEntryOption) {
                            case 1:
                                System.out.println("Select the role you want to create for the new user:");
                                System.out.println("1. Admin");
                                System.out.println("2. Buyer");

                                int roleSelect = sc.nextInt();
                                if (roleSelect == 1) {
                                    addAdminUser();
                                } else if (roleSelect == 2) {
                                    addBuyerUser();
                                } else {
                                    System.out.println("Wrong choice. Select 1 or 2. ");
                                }
                                break;
                            case 2:
                                getAllUsers();
                                break;

                            case 3:
                                deleteUserByLoginName();
                                break;
                            default:
                                System.out.println("Wrong choice. Select 1, 2 or 3.");
                        }
                        break;

                    case 2:
                        showProductCatalogModule();
                        break;

                    case 3:
                        System.out.println("Export list of all orders");
                        break;
                    default:
                        System.out.println("Wrong choice. Select 1, 2 or 3.");
                }

            } else if (hasBuyerRole) {

                System.out.println("Order process");

            } else {
                System.out.println("You don't have the appropriate rolls to access the modules!");
            }
        };
    }

    private void showAdministrationModule() {
        System.out.println("Select option: \n ");
        System.out.println("----------------------");
        System.out.println("1. User administration");
        System.out.println("2. Product catalog");
        System.out.println("3. List of all orders");
    }

    private void showUserAdministrationModule() {
        System.out.println("Select option: \n ");
        System.out.println("-----------------------------");
        System.out.println("1. New user entry");
        System.out.println("2. List of all users");
        System.out.println("3. Deleting an existing user");

    }

    private void showProductCatalogModule() {
        System.out.println("Select option: \n ");
        System.out.println("------------------------------------------");

        System.out.println("1. Entry of a new product into the catalog");
        System.out.println("2. List of all products in the catalog");
        System.out.println("3. Deleting an existing product");
    }

    private User login() {
        // ANSI escape codes for colors
        String reset = "\u001B[0m";
        String red = "\u001B[31m";

        String loginName;
        String password;

        Scanner sc = new Scanner(System.in);
        System.out.println("Please log in");
        System.out.print("Username: ");
        loginName = sc.nextLine();

        System.out.print("Password: ");
        password = sc.nextLine();
        User loggedUser = userService.checkUserCredentials(loginName, password);

        if (loggedUser == null) {
            System.out.println(red + "Incorect username or password!" + reset);
            return login();

        }
        return loggedUser;
    }


    private void createInitalUsersAndRoles() {
        Role admin = new Role("ROLE_ADMIN");
        Role customer = new Role("ROLE_BUYER");
        Role roleAdmin = roleRepository.findByName("ROLE_ADMIN");
        Role roleBuyer = roleRepository.findByName("ROLE_BUYER");
        if (roleAdmin == null || roleBuyer == null) {
            roleRepository.saveAll(List.of(admin, customer));
        }

        AdminUser adminUser = new AdminUser("hodzic", "12345d", "selko@gmail.com");
        String encryptPassworAdmin = BCrypt.hashpw("12345d", BCrypt.gensalt());
        adminUser.setPassword(encryptPassworAdmin);

        adminUser.addRole(admin);

        BuyerUser buyerUser = new BuyerUser("buyermax", "max123", "max@gmail.com", "Max", "Mustermann", LocalDate.of(1991, Month.APRIL, 20));
        Adress adress = new Adress("Rathausplatz 1", "85716", "Unterschleißheim", "Deutschland");
        String encryptPasswordBuyer = BCrypt.hashpw("max123", BCrypt.gensalt());
        buyerUser.setPassword(encryptPasswordBuyer);
        buyerUser.setAdress(adress);
        buyerUser.addRole(customer);

        User userAdmin = userRepository.findByLoginName("hodzic");
        User userBuyer = userRepository.findByLoginName("buyermax");
        if (userAdmin == null || userBuyer == null) {
            userRepository.saveAll(List.of(adminUser, buyerUser));
        }
    }

    private Pair<Boolean, Boolean> getUserRoles(User user) {

        roles = user.getRoles();
        Boolean hasAdminRole = roles.stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN"));
        Boolean hasBuyerRole = roles.stream().anyMatch(role -> role.getName().equals("ROLE_BUYER"));
        return Pair.of(hasAdminRole, hasBuyerRole);
    }


    private void addAdminUser() {
        Scanner sc = new Scanner(System.in);
        String loginName = null;
        String password = null;
        String email = null;

        try {

            loginName = insertLoginName(sc, loginName);
            password = insertPassword(sc, password);
            email = insertEmail(sc, email);

            AdminUser adminUser = new AdminUser();
            adminUser.setLoginName(loginName);
            String encryptPasswordAdmin = BCrypt.hashpw(password, BCrypt.gensalt());
            adminUser.setPassword(encryptPasswordAdmin);
            adminUser.setEmail(email);

            userController.addNewAdminUser(adminUser);
            System.out.println("User with ADMIN Role registered successfully!");
            System.out.println("Actual list of users: ");
            System.out.println("---------------------");
            getAllUsers();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void addBuyerUser() {
        Scanner sc = new Scanner(System.in);

        String loginName = null;
        String password = null;
        String email = null;
        String firstName = null;
        String lastName = null;
        LocalDate dateOfBirth = null;

        try {

            loginName = insertLoginName(sc, loginName);
            password = insertPassword(sc, password);
            email = insertEmail(sc, email);
            firstName = insertFirstName(sc, firstName);
            lastName = insertLastName(sc, lastName);
            dateOfBirth = insertDateOfBirth(sc, dateOfBirth);

            System.out.println("Adress");
            Adress adress = new Adress();
            insertAddressInformation(adress);

            BuyerUser buyerUser = new BuyerUser();
            buyerUser.setLoginName(loginName);
            String encryptPasswordBuyer = BCrypt.hashpw(password, BCrypt.gensalt());
            buyerUser.setPassword(encryptPasswordBuyer);
            buyerUser.setEmail(email);
            buyerUser.setFirstName(firstName);
            buyerUser.setLastName(lastName);
            buyerUser.setDateOfBirth(dateOfBirth);

            buyerUser.setAdress(adress);

            userController.addNewBuyerUser(buyerUser);
            System.out.println("User with BUYER Role registered successfully!");
            System.out.println("Actual list of users: ");
            System.out.println("---------------------");
            getAllUsers();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String insertLoginName(Scanner sc, String loginName) {
        while (loginName == null || loginName.trim().isEmpty()) {
            System.out.println("Enter Login Name: ");
            loginName = sc.nextLine();
        }
        return loginName;
    }


    private String insertPassword(Scanner sc, String password) {
        while (password == null || password.trim().isEmpty()) {
            System.out.println("Enter Password: ");
            password = sc.nextLine();
        }
        return password;
    }

    private String insertEmail(Scanner sc, String email) {
        while (email == null || email.trim().isEmpty()) {
            System.out.println("Enter Email: ");
            email = sc.nextLine();
        }
        return email;
    }

    private String insertFirstName(Scanner sc, String firstName) {
        while (firstName == null || firstName.trim().isEmpty()) {
            System.out.println("Enter First Name: ");
            firstName = sc.nextLine();
        }
        return firstName;
    }

    private String insertLastName(Scanner sc, String lastName) {
        while (lastName == null || lastName.trim().isEmpty()) {
            System.out.println("Enter Last Name: ");
            lastName = sc.nextLine();
        }
        return lastName;
    }

    private LocalDate insertDateOfBirth(Scanner sc, LocalDate dateOfBirth) {
        while (dateOfBirth == null || dateOfBirth.isEqual(LocalDate.MIN)) {
            System.out.println("Enter Date of Birth in format (yyyy-MM-dd): ");
            String dateInput = sc.nextLine();

            if (dateInput.isEmpty()) {
                dateOfBirth = LocalDate.MIN;
            } else {
                dateOfBirth = LocalDate.parse(dateInput);
            }
        }
        return dateOfBirth;
    }


    private void insertAddressInformation(Adress adress) {
        String streetAndNumber = null;
        String postcode = null;
        String city = null;
        String country = null;

        Scanner sc = new Scanner(System.in);
        System.out.println("----------------");

        streetAndNumber = insertStreetAndNumber(sc, streetAndNumber);
        postcode = insertPostcode(sc, postcode);
        city = insertCity(sc, city);
        country = insertCountry(sc, country);

        adress.setStreetAndNumber(streetAndNumber);
        adress.setPostcode(postcode);
        adress.setCity(city);
        adress.setCountry(country);

    }

    private String insertStreetAndNumber(Scanner sc, String streetAndNumber) {
        while (streetAndNumber == null || streetAndNumber.trim().isEmpty()) {
            System.out.println("Enter Street and Number: ");
            streetAndNumber = sc.nextLine();
        }
        return streetAndNumber;
    }

    private String insertPostcode(Scanner sc, String postcode) {
        while (postcode == null || postcode.trim().isEmpty()) {
            System.out.println("Enter Zip code: ");
            postcode = sc.nextLine();
        }
        return postcode;
    }

    private String insertCity(Scanner sc, String city) {
        while (city == null || city.trim().isEmpty()) {
            System.out.println("Enter City: ");
            city = sc.nextLine();
        }
        return city;
    }

    private String insertCountry(Scanner sc, String country) {
        while (country == null || country.trim().isEmpty()) {
            System.out.println("Enter Country: ");
            country = sc.nextLine();
        }
        return country;
    }


    private void getAllUsers() {
        List<UserDTO> allUsers = userController.getAllUsersWithoutPasswords();
        for (UserDTO user : allUsers) {
            System.out.println();
            System.out.println(user);
        }
    }

    private void deleteUserByLoginName() {
        Scanner sc1 = new Scanner(System.in);
        System.out.println("Enter the login name to be deleted:");
        String loginName = sc1.nextLine();
        userController.deleteUserByLoginName(loginName);
        System.out.println("User deleted successfully!");
        System.out.println("List of remaining users: ");
        System.out.println("----------------------------");
        getAllUsers();

    }
}
