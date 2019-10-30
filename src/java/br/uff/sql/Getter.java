/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.sql;

/**
 *
 * @author felipe
 */
public class Getter {
    private final Selector selector;
    
    public Getter(String tableName, Class klass) {
        this.selector = new Selector(tableName, klass);
    }
    
    public Selector select() {
        return selector;
    }
    
    public Selector select(String str) {
        return selector.select(str);
    }
}
