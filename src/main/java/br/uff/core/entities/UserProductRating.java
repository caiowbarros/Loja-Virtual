package br.uff.core.entities;

import java.util.Date;

public class UserProductRating {
    private Integer id;
    private Integer userId;
    private Integer rating;
    private String description;
    private String title;
    private Date createdAt;

    public UserProductRating() {
    }

    public UserProductRating(
        Integer id,
        Integer userId,
        Integer rating,
        String description,
        String title,
        Date createdAt
    ) {
        this.setId(id);
        this.setUserId(userId);
        this.setRating(rating);
        this.setDescription(description);
        this.setTitle(title);
        this.setCreatedAt(createdAt);
    }

    public UserProductRating(
        Integer userId,
        Integer rating,
        String description,
        String title,
        Date createdAt
    ) {
        this.setUserId(userId);
        this.setRating(rating);
        this.setDescription(description);
        this.setTitle(title);
        this.setCreatedAt(createdAt);
    }

    private void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getRating() {
        return rating;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
