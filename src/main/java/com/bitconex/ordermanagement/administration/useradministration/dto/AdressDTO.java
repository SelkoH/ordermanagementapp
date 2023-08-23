package com.bitconex.ordermanagement.administration.useradministration.dto;

public class AdressDTO {
    public Long id;
    public String streetAndNumber;
    public String postcode;
    public String city;
    public String country;

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

    @Override
    public String toString() {
        return
                "Id=" + id +
                        ", Street and Number='" + streetAndNumber + '\'' +
                        ", Postcode='" + postcode + '\'' +
                        ", City='" + city + '\'' +
                        ", Country='" + country + '\'' + " ";

    }
}
