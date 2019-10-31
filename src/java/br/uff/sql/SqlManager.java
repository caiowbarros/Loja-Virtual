/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.sql;

import br.uff.models.BaseModel;
import br.uff.mutators.Evaluator;
import br.uff.mutators.Inflector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author felipe
 */
public class SqlManager {
    private final Getter getter;
    private final Setter setter;
    private final Destroyer destroyer;
    
    public SqlManager(Class klass) {
        String tableName = Evaluator.getConstant(klass, "TABLE_NAME");
        getter = new Getter(tableName, klass);
        setter = new Setter(tableName, klass);
        destroyer = new Destroyer(tableName);
    }
    
    public ArrayList<BaseModel> all() throws SQLException {
        return select().run();
    }

    public BaseModel find(int id) throws SQLException {
        ArrayList<BaseModel> result = select().where("id = " + id).run();
        if(result.isEmpty()) return null;
        return result.get(0);
    }
    
    public BaseModel findBy(String condition) throws SQLException {
        ArrayList<BaseModel> result = select().where(condition).run();
        if(result.isEmpty()) return null;
        return result.get(0);
    }

    public Selector select() {
        return getter.select();
    }
    
    public Selector select(String str) {
        return getter.select(str);
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
    
    public static int bruteUpdate(String query, String[] bind) throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        PreparedStatement cleanStatement = connection.prepareStatement(query);
        PreparedStatement bindedStatement = Inflector.bind(cleanStatement, bind);
        return bindedStatement.executeUpdate();
    }
    
    public static ResultSet bruteExecute(String query, String[] bind) throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        PreparedStatement cleanStatement = connection.prepareStatement(query);
        PreparedStatement bindedStatement = Inflector.bind(cleanStatement, bind);
        return bindedStatement.executeQuery();
    }
    
    public static void bruteExecute(String[] queries, String[][] bind, boolean autoCommit) throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        try {
            connection.setAutoCommit(autoCommit);
            Integer aux = 0;
            for (String query : queries) {
                // pega comando e verifica se index existe e pega bind
                bruteExecute(query, aux < bind.length ? bind[aux] : null);
                // incrementa aux
                aux += 1;
            }
            connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
            throw new SQLException(ex.getMessage());
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
