package br.uff.core.entities;

public class Address {
    private Integer id;
    private String name;
    private Integer userId;
    private Integer zipcode;
    private String addressStreet;
    private String city;
    private String state;
    private String country;

    public Address() {
    }

    public Address(
        Integer id,
        String name,
        Integer userId,
        Integer zipcode,
        String address,
        String city,
        String state,
        String country
    ) {
        this.setId(id);
        this.setName(name);
        this.setUserId(userId);
        this.setZipcode(zipcode);
        this.setAddress(address);
        this.setCity(city);
        this.setState(state);
        this.setCountry(country);
    }

    public Address(
        String name,
        Integer userId,
        Integer zipcode,
        String address,
        String city,
        String state,
        String country
    ) {
        this.setName(name);
        this.setUserId(userId);
        this.setZipcode(zipcode);
        this.setAddress(address);
        this.setCity(city);
        this.setState(state);
        this.setCountry(country);
    }

    private void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getZipcode() {
        return zipcode;
    }

    public void setZipcode(Integer zipcode) {
        this.zipcode = zipcode;
    }

    public String getAddress() {
        return addressStreet;
    }

    public void setAddress(String address) {
        this.addressStreet = address;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
