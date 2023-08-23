package com.bitconex.ordermanagement.administration.useradministration.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
//@DiscriminatorValue("ROLE_CUSTOMER")
public class BuyerUser extends User {

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "adress_id")
    private Adress adress;

    public BuyerUser() {
    }

    public BuyerUser(String loginName, String password, String email, String firstName,
                     String lastName, LocalDate dateOfBirth) {
        super(loginName, password, email);
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;

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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Adress getAdress() {
        return adress;
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
    }


}
