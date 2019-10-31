/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.models;

import java.util.Map;

/**
 *
 * @author felipe
 */
public class Address extends BaseModel {
    public static final String TABLE_NAME = "address";

    private int id;
    private String name;
    private int zipcode;
    private String address;
    private String city;
    private String state;
    private String country;
    private int userId;

    public Address(String name, int zipcode, String address, String city, String state, String country, int userId) {
        this.name = name;
        this.zipcode = zipcode;
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.userId = userId;
    }

    public Address(Map<String, Object> attrs) {
        super(attrs);
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getZipcode() {
        return this.zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    
    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }
    
    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
