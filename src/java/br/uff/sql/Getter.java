/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.sql;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author felipe
 */
public class Getter {
    private final Selector selector;
    private final Counter counter;
    
    public Getter(String tableName, Class klass) {
        this.selector = new Selector(tableName, klass);
        this.counter = new Counter(tableName, klass);
    }
    
    public int count() throws SQLException {
        return counter.count();
    }
    
    public Selector select() {
        return selector;
    }
    
    public Selector select(String str) {
        return selector.select(str);
    }
}
