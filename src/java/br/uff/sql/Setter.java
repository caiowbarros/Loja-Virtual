/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.sql;

import java.sql.Connection;

/**
 *
 * @author felipe
 */
public class Setter {
    private final Inserter INSERTER;
    private final Updater UPDATER;
    
    public Setter(String tableName, Class klass) {
        this.INSERTER = new Inserter(tableName, klass);
        this.UPDATER = new Updater(tableName, klass);
    }
    
    public Inserter insert() {
        return this.INSERTER;
    }
    
    public Updater update() {
        return this.UPDATER;
    }
}
