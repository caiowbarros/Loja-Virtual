/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.application.models;

import java.util.Map;

/**
 *
 * @author felipe
 */
public class Category extends BaseModel {
    public static final String TABLE_NAME = "category";
    private int id;
    private String name;
    private int fatherCategoryId;

    public Category(int id, String name, int fatherCategoryId) {
        this.id = id;
        this.name = name;
        this.fatherCategoryId = fatherCategoryId;
    }
    
    public Category(Map<String, Object> attrs) {
        super(attrs);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFatherCategoryId() {
        return fatherCategoryId;
    }

    public void setFatherCategoryId(int fatherCategoryId) {
        this.fatherCategoryId = fatherCategoryId;
    }
    
    
}
