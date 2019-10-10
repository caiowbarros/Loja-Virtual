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
public class Role extends BaseModel {
    private int id;
    private String name;
    
    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public Role(Map<String, Object> attrs) {
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
}
