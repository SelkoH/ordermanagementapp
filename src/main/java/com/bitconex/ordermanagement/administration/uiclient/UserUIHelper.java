package com.bitconex.ordermanagement.administration.uiclient;

import com.bitconex.ordermanagement.administration.controller.UserController;
import com.bitconex.ordermanagement.administration.dto.UserDTO;
import com.bitconex.ordermanagement.administration.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

@Component
public class UserUIHelper {

    private final UserController userController;
    private Set<Role> roles;

    @Autowired
    public UserUIHelper(UserController userController) {
        this.userController = userController;
    }

    public void showAdministrationModule() {
        System.out.println();
        System.out.println("Select option: \n ");
        System.out.println("----------------------");
        System.out.println("1. User administration");
        System.out.println("2. Product catalog");
        System.out.println("3. List of all orders");
    }

    public void showUserAdministrationModule() {
        System.out.println("Select option: \n ");
        System.out.println("-----------------------------");
        System.out.println("1. New user entry");
        System.out.println("2. List of all users");
        System.out.println("3. Deleting an existing user");

    }

    public void createRoleforUser() {
        Scanner sc = new Scanner(System.in);
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
    }

    public void entryIntoUserAdministrationModule() {
        showUserAdministrationModule();
        Scanner sc = new Scanner(System.in);

        int userEntryOption = sc.nextInt();

        switch (userEntryOption) {
            case 1:
                createRoleforUser();
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
    }


    public Pair<Boolean, Boolean> getUserRoles(User user) {

        roles = user.getRoles();
        Boolean hasAdminRole = roles.stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN"));
        Boolean hasBuyerRole = roles.stream().anyMatch(role -> role.getName().equals("ROLE_BUYER"));
        return Pair.of(hasAdminRole, hasBuyerRole);
    }

    public void addAdminUser() {
        String loginName;
        String password;
        String email;

        try {

            loginName = insertLoginName();
            password = insertPassword();
            email = insertEmail();

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

    public void addBuyerUser() {
        String loginName;
        String password;
        String email;
        String firstName;
        String lastName;
        LocalDate dateOfBirth;

        try {

            loginName = insertLoginName();
            password = insertPassword();
            email = insertEmail();
            firstName = insertFirstName();
            lastName = insertLastName();
            dateOfBirth = insertDateOfBirth();

            System.out.println("Adress");
            Address address = new Address();
            insertAddressInformation(address);

            BuyerUser buyerUser = new BuyerUser();
            buyerUser.setLoginName(loginName);
            String encryptPasswordBuyer = BCrypt.hashpw(password, BCrypt.gensalt());
            buyerUser.setPassword(encryptPasswordBuyer);
            buyerUser.setEmail(email);
            buyerUser.setFirstName(firstName);
            buyerUser.setLastName(lastName);
            buyerUser.setDateOfBirth(dateOfBirth);

            buyerUser.setAdress(address);

            userController.addNewBuyerUser(buyerUser);
            System.out.println("User with BUYER Role registered successfully!");
            System.out.println("Actual list of users: ");
            System.out.println("---------------------");
            getAllUsers();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String insertLoginName() {
        Scanner sc = new Scanner(System.in);
        String loginName = null;

        while (loginName == null || loginName.trim().isEmpty()) {
            System.out.println("Enter Login Name: ");
            loginName = sc.nextLine();

            if (loginName.length() < 3) {
                System.out.println("Login name is to short to be added.");
                loginName = null;
            }
        }
        return loginName;
    }


    private String insertPassword() {
        Scanner sc = new Scanner(System.in);
        String password = null;

        while (password == null || password.trim().isEmpty()) {
            System.out.println("Enter Password: ");
            password = sc.nextLine();

            if (password.length() < 4) {
                System.out.println("Password is to short. Try again");
                password = null;
            }
        }
        return password;
    }

    private String insertEmail() {
        Scanner sc = new Scanner(System.in);
        String email = null;

        while (email == null || email.trim().isEmpty()) {
            System.out.println("Enter Email: ");
            email = sc.nextLine();

            if (email.length() <= 10 && !email.contains("@")) {
                System.out.println("Email is to short or  does not contains @ Try again");
                email = null;
            }
        }
        return email;
    }

    private String insertFirstName() {
        Scanner sc = new Scanner(System.in);
        String firstName = null;

        while (firstName == null || firstName.trim().isEmpty()) {
            System.out.println("Enter First Name: ");
            firstName = sc.nextLine();

            if (firstName.length() < 3) {
                System.out.println("First Name is to short. Try again");
                firstName = null;
            }

        }
        return firstName;
    }

    private String insertLastName() {
        Scanner sc = new Scanner(System.in);
        String lastName = null;

        while (lastName == null || lastName.trim().isEmpty()) {
            System.out.println("Enter Last Name: ");
            lastName = sc.nextLine();

            if (lastName.length() < 3) {
                System.out.println("First Name is to short. Try again");
                lastName = null;
            }
        }
        return lastName;
    }

    private LocalDate insertDateOfBirth() {
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        LocalDate dateOfBirth;

        while (true) {
            System.out.println("Enter Date of Birth in format (yyyy.MM.dd): ");
            String dateInput = sc.nextLine();

            if (dateInput.trim().isEmpty()) {
                System.out.println("You did not enter a date. Try again");
                continue;
            }
            try {
                dateOfBirth = LocalDate.parse(dateInput, dateTimeFormatter);

            } catch (DateTimeParseException e) {
                System.out.println("Incorrect date format. Try again" + e.getMessage());
                continue;
            }
            break;
        }
        return dateOfBirth;
    }


    private void insertAddressInformation(Address address) {
        String streetAndNumber;
        Long postcode;
        String city;
        String country;
        System.out.println("----------------");

        streetAndNumber = insertStreetAndNumber();
        postcode = insertPostcode();
        city = insertCity();
        country = insertCountry();

        address.setStreetAndNumber(streetAndNumber);
        address.setPostcode(postcode);
        address.setCity(city);
        address.setCountry(country);

    }

    private String insertStreetAndNumber() {
        Scanner sc = new Scanner(System.in);
        String streetAndNumber = null;

        while (streetAndNumber == null || streetAndNumber.trim().isEmpty()) {
            System.out.println("Enter Street and Number: ");
            streetAndNumber = sc.nextLine();

            if (streetAndNumber.length() <= 5) {
                System.out.println("Street and Number are to short.");
                streetAndNumber = null;
            }
        }
        return streetAndNumber;
    }

    private Long insertPostcode() {
        Scanner sc = new Scanner(System.in);
        Long postcode;

        do {
            System.out.println("Enter Postcode: ");

            if (sc.hasNextLong()) {
                postcode = sc.nextLong();

                if (postcode == 0) {
                    System.out.println("Postcode should not be 0");
                    postcode = null;
                }
            } else {
                System.out.println("Invalid input format for postcode. Try again.");
                sc.nextLine();
                postcode = null;
            }
        } while (postcode == null);

        return postcode;

    }

    private String insertCity() {
        Scanner sc = new Scanner(System.in);
        String city = null;

        while (city == null || city.trim().isEmpty()) {
            System.out.println("Enter City: ");
            city = sc.nextLine();

            if (city.length() < 3) {
                System.out.println("City name is to short.");
                city = null;
            }
        }
        return city;
    }

    private String insertCountry() {
        Scanner sc = new Scanner(System.in);
        String country = null;

        while (country == null || country.trim().isEmpty()) {
            System.out.println("Enter Country: ");
            country = sc.nextLine();

            if (country.length() < 3) {
                System.out.println("Country name is to short.");
                country = null;
            }
        }
        return country;
    }


    public void getAllUsers() {
        List<UserDTO> allUsers = userController.getAllUsersWithoutPasswords();
        if (allUsers != null) {
            for (UserDTO user : allUsers) {
                System.out.println();
                System.out.println(user);
            }
        }
    }

    public void deleteUserByLoginName() {
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
