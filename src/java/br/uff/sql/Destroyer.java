/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author felipe
 */
public class Destroyer {
    protected Connection connection = null;
    private final String delete;
    private String where;
    
    public Destroyer(String tableName, Connection connection) {
        this.reload();
        this.connection = connection;
        this.delete = "delete from" + tableName;
    }
    
    private void reload() {
        this.where = "";
    }
    
    public int run() throws SQLException {
        PreparedStatement statement = connection.prepareStatement(this.build());
        return statement.executeUpdate();
    }
    
    private String build() {
        return this.delete + " " + this.where;
    }
    
    public Destroyer where(String str) {
        this.where = str;
        return this;
    }
}
