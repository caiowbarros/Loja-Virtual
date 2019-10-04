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
    private final ArrayList<String> joins;
    private String limit;
    private String offset;
    private Class child = null;
    protected Connection connection = null;
    
    public Selector(String tableName, Connection connection, Class child) {
        this.select = "";
        this.from = "from " + tableName;
        this.where = "";
        this.joins = new ArrayList();
        this.limit = "";
        this.offset = "";
        this.child = child;
        this.connection = connection;
    }
    
    public ArrayList<BaseModel> run() {

        ArrayList<BaseModel> models = new ArrayList();
        try {
            String b = this.build();

            PreparedStatement statement = connection.prepareStatement(b);
            ResultSet result = statement.executeQuery();
            while(result.next()) {
                ResultSetMetaData meta = result.getMetaData();
                int colCount = meta.getColumnCount();
                Map<String, Object> persitedAttrs = new HashMap();
                for (int i = 1; i <= colCount ; i++){
                    String col_name = meta.getColumnName(i);
                    persitedAttrs.put(col_name, result.getObject(col_name));
                }
                Constructor<BaseModel> constructor = child.getConstructor(Map.class);
                models.add(constructor.newInstance(persitedAttrs));
            }
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException | NoSuchMethodException ex) {
            Logger.getLogger(BaseModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Selector.class.getName()).log(Level.SEVERE, null, ex);
        }
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
