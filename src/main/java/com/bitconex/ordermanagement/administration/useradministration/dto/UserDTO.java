package com.bitconex.ordermanagement.administration.useradministration.dto;

import java.util.Set;

public class UserDTO {
    public Long id;
    public String loginName;
    public String email;
    public String firstName;
    public String lastName;
    public String dateOfBirth;

    public Set<RoleDTO> roles;

    public AdressDTO adress;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public AdressDTO getAdress() {
        return adress;
    }

    public void setAdress(AdressDTO adress) {
        this.adress = adress;
    }

    public Set<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDTO> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User: " + '\n' +
                "Id=" + id +
                ", Login name='" + loginName + '\'' +
                ", Email='" + email + '\'' +
                ", First Name='" + firstName + '\'' +
                ", Last Name='" + lastName + '\'' +
                ", Date of Birth='" + dateOfBirth + '\'' + '\n' +
                "Role: " + roles + '\n' +
                "Adress: " + adress +
                '}';
    }
}
