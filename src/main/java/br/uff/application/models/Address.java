/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.application.models;

import java.util.Map;

/**
 *
 * @author felipe
 */
public class Address extends BaseModel {

    private int id = 0;
    private String name = "";
    private String zipcode = "";
    private String address = "";
    private String city = "";
    private String state = "";
    private String country = "";

    public Address(String name, String zipcode, String address, String city, String state, String country) {
        this.name = name;
        this.zipcode = zipcode;
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
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

    public String getZipcode() {
        return this.zipcode;
    }

    public void setZipcode(String zipcode) {
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
}
