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
public class SqlManager {
    private Getter getter;
    private Setter setter;
    private Destroyer destroyer;
    
    public SqlManager(String tableName, Connection connection, Class klass) {
        getter = new Getter(tableName, connection, klass);
        setter = new Setter(tableName, connection, klass);
        destroyer = new Destroyer(tableName, connection);
    }
    
    public Selector select() {
        return getter.select();
    }
    
    public Selector select(String str) {
        return getter.select(str);
    }
    
    public int count() throws SQLException {
        return getter.count();
    }
    
    public Inserter insert() {
        return setter.insert();
    }
    
    public Updater update() {
        return setter.update();
    }
    
    public Destroyer delete() {
        return destroyer;
    }
}
