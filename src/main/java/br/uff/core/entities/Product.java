package br.uff.core.entities;

import java.util.Date;

public class Product {
    private Integer id;
    private String name;
    private Double price;
    private String description;
    private String img;
    private Integer categoryId;
    private Integer quantity;
    private Date createdAt;

    public Product(){
    }

    public Product(
        String name,
        Double price,
        String description,
        String img,
        Integer categoryId,
        Integer quantity,
        Date createdAt
    ) {
        this.setName(name);
        this.setPrice(price);
        this.setDescription(description);
        this.setImg(img);
        this.setCategoryId(categoryId);
        this.setQuantity(quantity);
        this.setCreatedAt(createdAt);
    }

    public Product(
        Integer id,
        String name,
        Double price,
        String description,
        String img,
        Integer categoryId,
        Integer quantity,
        Date createdAt
    ) {
        this.setId(id);
        this.setName(name);
        this.setPrice(price);
        this.setDescription(description);
        this.setImg(img);
        this.setCategoryId(categoryId);
        this.setQuantity(quantity);
        this.setCreatedAt(createdAt);
    }

    private void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}