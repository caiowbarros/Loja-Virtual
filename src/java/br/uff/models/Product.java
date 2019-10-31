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
public class Product extends BaseModel {
    public static final String TABLE_NAME = "products";

    private int id = 0;
    private String name = "";
    private String price = "";
    private String description = "";
    private String img = "";
    private int categoryId = 0;
    private int quantity = 0;
    private String createdAt = "";

    public Product(String name, String price, String description, String img, int categoryId, int quantity, String createdAt) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.img = img;
        this.categoryId = categoryId;
        this.quantity = quantity;
        this.createdAt = createdAt;
    }

    public Product(Map<String, Object> attrs) {
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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return this.img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice() {
        return this.price;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

}
