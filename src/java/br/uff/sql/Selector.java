/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.sql;

import br.uff.models.BaseModel;
import br.uff.mutators.Inflector;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author felipe
 */
public class Selector {
    private String select;
    private final String from;
    private String where;
    private ArrayList<String> joins;
    private String limit;
    private String offset;
    private Class klass = null;
    protected Connection connection = null;
    
    public Selector(String tableName, Connection connection, Class klass) {
        this.reload();
        this.from = "from " + tableName;
        this.where = "";
        this.klass = klass;
        this.connection = connection;
    }
    
    protected void reload() {
        this.select = "";
        this.joins = new ArrayList();
        this.limit = "";
        this.offset = "";
    }
    
    public ArrayList<BaseModel> run() {
        ArrayList<BaseModel> models = new ArrayList();
        try {
            PreparedStatement statement = connection.prepareStatement(this.build());
            ResultSet result = statement.executeQuery();
            while(result.next()) {
                ResultSetMetaData meta = result.getMetaData();
                int colCount = meta.getColumnCount();
                Map<String, Object> persitedAttrs = new HashMap();
                for (int i = 1; i <= colCount ; i++){
                    String col_name = meta.getColumnName(i);
                    persitedAttrs.put(col_name, result.getObject(col_name));
                }
                Constructor<BaseModel> constructor = klass.getConstructor(Map.class);
                models.add(constructor.newInstance(persitedAttrs));
            }
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException | NoSuchMethodException ex) {
            Logger.getLogger(BaseModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Selector.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.reload();
        return models;
    }
    
    protected String build() {
        StringBuilder sb = new StringBuilder();
        if(select.isEmpty()) sb.append("select *"); else sb.append(select);
        sb.append(" ");
        sb.append(from);
        sb.append(" ");
        sb.append(where);
        sb.append(" ");
        sb.append(Inflector.joins(this.joins));
        sb.append(" ");
        sb.append(limit);
        sb.append(" ");
        sb.append(offset);
        return sb.toString();
    }
    
    public Selector select(String select) {
        this.select = "select " + select;
        return this;
    }
    
    public Selector where(String where) {
        this.where = "where " + where;
        return this;
    }
    
    public Selector addJoins(String joinStatement) {
        this.joins.add(joinStatement);
        return this;
    }
    
    public Selector limit(int limit) {
        this.limit = "limit " + String.valueOf(limit);
        return this;
    }
    
    public Selector offset(int offset) {
        this.offset = "offset " + String.valueOf(offset);
        return this;
    }
}
