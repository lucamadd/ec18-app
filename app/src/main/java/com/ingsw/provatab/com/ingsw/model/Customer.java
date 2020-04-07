package com.ingsw.provatab.com.ingsw.model;

import java.io.Serializable;

public class Customer implements Serializable {

    private String firstname;
    private String lastname;
    private String address;
    private String city;
    private String province;
    private String email;
    private String password;



    public Customer() {}

    public Customer(String firstname, String lastname, String address, String city, String province, String email, String password) {
        setFirstName(firstname);
        setLastName(lastname);
        setAddress(address);
        setCity(city);
        setProvince(province);
        setEmail(email);
        setPasswd(password);
    }
    public void setFirstName(String firstname) {
        this.firstname=firstname;
    }
    public void setLastName(String lastname) {
        this.lastname=lastname;
    }
    public void setAddress(String address) {
        this.address=address;
    }
    public void setCity(String city) {
        this.city=city;
    }
    public void setProvince(String province) {
        this.province=province;
    }

    public void setEmail(String email) {
        this.email=email;
    }
    public void setPasswd(String password) {
        this.password=password;
    }
    public String getFirstName() {
        return firstname;
    }
    public String getLastName() {
        return lastname;
    }
    public String getEmail() {
        return email;
    }
    public String getPasswd() {
        return password;
    }
    public String getAddress() {
        return address;
    }
    public String getCity() {
        return city;
    }
    public String getProvince() {
        return province;
    }



}
