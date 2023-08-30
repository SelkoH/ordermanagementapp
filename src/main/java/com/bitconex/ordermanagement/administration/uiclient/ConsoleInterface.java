package com.bitconex.ordermanagement.administration.uiclient;

import com.bitconex.ordermanagement.administration.entity.*;
import com.bitconex.ordermanagement.administration.repository.RoleRepository;
import com.bitconex.ordermanagement.administration.repository.UserRepository;
import com.bitconex.ordermanagement.administration.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.util.Pair;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Scanner;


@Configuration
public class ConsoleInterface {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private RoleRepository roleRepository;

    @Bean
    CommandLineRunner commandLineRunner(UserUIHelper userUIHelper, ProductUIHelper productUIHelper) {
        return args -> {

            Scanner sc = new Scanner(System.in);
            createInitalUsersAndRoles();
            int selection;

            Boolean hasAdminRole;
            Boolean hasBuyerRole;

            User loggedUser = login();
            Pair<Boolean, Boolean> hasRoles = userUIHelper.getUserRoles(loggedUser);
            hasAdminRole = hasRoles.getFirst();
            hasBuyerRole = hasRoles.getSecond();


            if (hasAdminRole) {

                do {
                    userUIHelper.showAdministrationModule();
                    selection = sc.nextInt();

                    switch (selection) {
                        case 1:
                            userUIHelper.entryIntoUserAdministrationModule();
                            break;
                        case 2:
                            productUIHelper.entryIntoProductCatalogModule();
                            break;
                        case 3:
                            System.out.println("Export list of all orders");
                            break;
                        default:
                            System.out.println("Wrong choice. Select 1, 2 or 3.");
                    }
                } while (true);

            } else if (hasBuyerRole) {

                System.out.println("Order process");

            } else {
                System.out.println("You don't have the appropriate rolls to access the modules!");
            }
        };
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
        Address address = new Address("Rathausplatz 1", 85716L, "Unterschleißheim", "Deutschland");
        String encryptPasswordBuyer = BCrypt.hashpw("max123", BCrypt.gensalt());
        buyerUser.setPassword(encryptPasswordBuyer);
        buyerUser.setAdress(address);
        buyerUser.addRole(customer);

        User userAdmin = userRepository.findByLoginName("hodzic");
        User userBuyer = userRepository.findByLoginName("buyermax");
        if (userAdmin == null || userBuyer == null) {
            userRepository.saveAll(List.of(adminUser, buyerUser));
        }
    }
}
