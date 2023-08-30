package com.bitconex.ordermanagement.administration.controller;

import com.bitconex.ordermanagement.administration.dto.UserDTO;
import com.bitconex.ordermanagement.administration.entity.AdminUser;
import com.bitconex.ordermanagement.administration.entity.BuyerUser;
import com.bitconex.ordermanagement.administration.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/user-administration")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @Autowired
    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }


    @GetMapping(path = "/users")
    public List<UserDTO> getAllUsersWithoutPasswords() {
        return userServiceImpl.getAllUsersWithoutPasswords();

    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/addAdminUser")
    public void addNewAdminUser(@RequestBody AdminUser adminUser) throws RoleNotFoundException {
        userServiceImpl.registerAdminUser(adminUser);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/addCustomerUser")
    public void addNewBuyerUser(@RequestBody BuyerUser buyerUser) throws RoleNotFoundException {
        userServiceImpl.registerBuyerUser(buyerUser);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "{loginName}")
    public void deleteUserByLoginName(@PathVariable("loginName") String loginName) {
        userServiceImpl.deleteUserByLoginName(loginName);
    }

}
