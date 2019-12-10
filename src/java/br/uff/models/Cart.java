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
public class Cart extends BaseModel {
    public static final String TABLE_NAME = "carts";
    private int id;
    private int userId;
    private String ip;
    private String createdAt;
    
    public Cart(int id, int userId, String ip, String createdAt) {
        this.id = id;
        this.userId = userId;
        this.ip = ip;
        this.createdAt = createdAt;
    }
    
    public Cart(Map<String, Object> attrs) {
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    
    
}
