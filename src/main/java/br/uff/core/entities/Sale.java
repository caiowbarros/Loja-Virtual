package br.uff.core.entities;

import java.util.Date;

public class Sale {
    private Integer id;
    private Integer cartId;
    private Double totalPrice;
    private Date createdAt;
    private Integer addressId;
    private Integer userId;

    public Sale() {
    }

    public Sale(
        Integer id,
        Integer cartId,
        Double totalPrice,
        Date createdAt,
        Integer addressId,
        Integer userId
    ) {
        this.setId(id);
        this.setCartId(cartId);
        this.setTotalPrice(totalPrice);
        this.setCreatedAt(createdAt);
        this.setAddressId(addressId);
        this.setUserId(userId);
    }

    public Sale(
        Integer cartId,
        Double totalPrice,
        Date createdAt,
        Integer addressId,
        Integer userId
    ) {
        this.setCartId(cartId);
        this.setTotalPrice(totalPrice);
        this.setCreatedAt(createdAt);
        this.setAddressId(addressId);
        this.setUserId(userId);
    }
    
    private void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }
}
