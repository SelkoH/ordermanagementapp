package com.bitconex.ordermanagement.administration.useradministration.entity;

import com.bitconex.ordermanagement.administration.useradministration.entity.Role;
import com.bitconex.ordermanagement.administration.useradministration.entity.User;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;


@Entity
//@DiscriminatorValue("ROLE_ADMIN")
public class AdminUser extends User {


    public AdminUser() {

    }

    public AdminUser(String loginName, String password, String email) {
        super(loginName, password, email);

    }



}
