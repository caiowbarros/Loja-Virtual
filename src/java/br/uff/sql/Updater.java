/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.sql;

import br.uff.mutators.Inflector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author felipe
 */
public class Updater {
    private Class klass = null;
    protected Connection connection = null;
    private final String update;
    private String where;
    private HashMap<String, String> attrs;
    
    public Updater(String tableName, Class klass) {
        this.reload();
        this.update = "update " + tableName;
        this.klass = klass;
        this.connection = ConnectionManager.getConnection();
    }
    
    private void reload() {
        this.where = "";
        this.attrs = new HashMap();
    }
    
    public int run() throws SQLException {
        int affectedRows = 0;
        PreparedStatement statement = connection.prepareStatement(this.build());
        try {
            affectedRows = statement.executeUpdate();
            this.reload();
        } catch (SQLException ex) {
            throw ex;
        }
        return affectedRows;
    }
    
    public Updater set(Map.Entry<String,Object>... attrs ) {
        for(Map.Entry<String,Object> attr : attrs) {
            addAttr(attr);
        }
        return this;
    }
    
    public Updater set(HashMap<String,Object> attrs ) {
        attrs.entrySet().forEach((attr) -> {
            addAttr(attr);
        });
        return this;
    }
    
    private void addAttr(Map.Entry<String, Object> attr) {
        String key = attr.getKey();
        String parsedValue;
        Object value = attr.getValue();
        if (value instanceof String) {
            parsedValue = Inflector.toQuotedString(String.valueOf(value));
        } else if (value == null) {
            parsedValue = "NULL";
        } else {
            parsedValue  = String.valueOf(value);
        }
        this.attrs.put(key, parsedValue);
    }
    
    public Updater where(String where) {
        this.where = "where " + where;
        return this;
    }
    
    private String build() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.update);
        sb.append(" ");
        sb.append("set ");
        for(Map.Entry<String, String> entry : this.attrs.entrySet()) {
            sb.append(entry.getKey());
            sb.append(" = ");
            sb.append(entry.getValue());
            sb.append(",");
        }
        if(sb.toString().contains(","))
            sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append(" ");
        sb.append(this.where);
        return sb.toString();
    }
}
