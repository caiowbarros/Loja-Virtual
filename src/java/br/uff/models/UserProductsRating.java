/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.models;

import br.uff.sql.SqlManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author felipe
 */
public class UserProductsRating extends BaseModel {
    public static final String TABLE_NAME = "user_produts_rating";
    private int id;
    private int userId;
    private int productId;
    private boolean rating;
    private String description;
    private String title;
    private Date createdAt;
    
    public UserProductsRating(int id, int userId, int productId, 
            boolean rating, String description, String title, Date createdAt) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.rating = rating;
        this.description = description;
        this.title = title;
        this.createdAt = createdAt;
    }
    
    public UserProductsRating(Map<String, Object> attrs) {
        super(attrs);
    }
    
    public Product getProduct() throws SQLException {
        return (Product) new SqlManager(Product.class).find(this.productId);
    }
    
    public User getUser() throws SQLException {
        return (User) new SqlManager(User.class).find(this.userId);
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getUserId() {
        return this.userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public int getProductId() {
        return this.productId;
    }
    
    public void setProductId(int productId) {
        this.productId = productId;
    }
    
    public boolean getRating() {
        return this.rating;
    }
    
    public void setRating(boolean rating) {
        this.rating = rating;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public Date getCreatedAt() {
        return this.createdAt;
    }
    
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
