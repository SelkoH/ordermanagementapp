package com.bitconex.ordermanagement.administration.entity;

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
