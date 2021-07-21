package br.uff.core.entities;

import java.util.Date;

public class Cart {
    private Integer id;
    private Integer userId;
    private String ip;
    private Date createdAt;


    public Cart(
        Integer id,
        Integer userId,
        String ip,
        Date createdAt
    ) {
        this.setId(id);
        this.setUserId(userId);
        this.setIp(ip);
        this.setCreatedAt(createdAt);
    }

    public Cart(
        Integer userId,
        String ip,
        Date createdAt
    ) {
        this.setUserId(userId);
        this.setIp(ip);
        this.setCreatedAt(createdAt);
    }

    public Cart() {
    }

    private void setId(Integer id) {
        this.id = id;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Integer getId() {
        return id;
    }
}
