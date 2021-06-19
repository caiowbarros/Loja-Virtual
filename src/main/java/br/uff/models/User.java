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
public class User extends BaseModel {
    private int id;
    private String name;
    private String email;
    private String password;
    private int roleId;
    
    public User(String name, String email, String password, int roleId) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.roleId = roleId;
    }
    
    public User(Map<String, Object> attrs) {
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
    
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public int getRoleId() {
        return this.roleId;
    }
    
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
