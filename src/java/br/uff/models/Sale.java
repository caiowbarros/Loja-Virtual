/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.models;

/**
 *
 * @author felipe
 */
public class Sale extends BaseModel {
    public static final String TABLE_NAME = "sales";
    private int id;
    private int cartId;
    private float totalPrice;
    private String createdAt;
    private int addressId;
    private int userId;

    public Sale(int id, int cartId, float totalPrice, String createdAt, int addressId, int userId ) {
        this.id = id;
        this.cartId = cartId;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.addressId = addressId;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    
}
