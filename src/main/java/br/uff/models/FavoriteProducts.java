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
public class FavoriteProducts extends BaseModel {
    public static final String TABLE_NAME = "favorite_products";
    private int id;
    private int userId;
    private int productId;

    public FavoriteProducts(int id, int userId, int productId) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
    }
    
    public FavoriteProducts(Map<String, Object> attrs) {
        super(attrs);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
    
    
}
