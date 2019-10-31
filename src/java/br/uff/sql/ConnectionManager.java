/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author felipe
 */
public class ConnectionManager {
    private static Connection connection;
    
    public static void connect() throws ClassNotFoundException, SQLException {
        if(connection == null) {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
        }
    }
    
    public static Connection getConnection() {
        return connection;
    }
    
    public static void close() throws SQLException {
        connection.close();
        connection = null;
    }
}
