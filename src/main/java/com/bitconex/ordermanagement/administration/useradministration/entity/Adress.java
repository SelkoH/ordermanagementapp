package com.bitconex.ordermanagement.administration.useradministration.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "adress")
public class Adress {

    @Id
    @SequenceGenerator(name = "adress_sequence",
            sequenceName = "adress_sequence",
            allocationSize = 1)

    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "adress_sequence")

    private Long id;

    @Column(nullable = false)
    private String streetAndNumber;

    @Column(nullable = false)
    private String postcode;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String country;

    public Adress() {
    }

    public Adress(String streetAndNumber, String postcode, String city, String country) {
        this.streetAndNumber = streetAndNumber;
        this.postcode = postcode;
        this.city = city;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreetAndNumber() {
        return streetAndNumber;
    }

    public void setStreetAndNumber(String streetAndNumber) {
        this.streetAndNumber = streetAndNumber;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
