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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.util.Pair;

/**
 *
 * @author felipe
 */
public class Inserter {
    private Class klass = null;
    protected Connection connection = null;
    private final String insert;
    private ArrayList<String> columns;
    private ArrayList<String> values;
    private HashMap<String, Object> attrs;
    
    public Inserter(String tableName, Connection connection, Class klass) {
        this.reload();
        this.insert = "insert into" + tableName;
        this.klass = klass;
        this.connection = connection;
    }
    
    protected void reload() {
        this.columns = new ArrayList();
        this.values = new ArrayList();
        this.attrs = new HashMap();
    }
    
    public BaseModel run() throws SQLException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        PreparedStatement statement = connection.prepareStatement(this.build());
        if(statement.executeUpdate() != 1) throw new SQLException();
        this.reload();
        return model();
    }
    
    private String build() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.insert);
        sb.append("(");
        sb.append(Inflector.joins(this.columns, ','));
        sb.append(")");
        sb.append(" values(");
        sb.append(Inflector.joins(this.values, ','));
        sb.append(")");
        return sb.toString();
    }
    
    public Inserter values(Pair<String,Object>... attrs ) {
        for(Pair<String,Object> attr : attrs) {
            columns.add(attr.getKey());
            Object value = attr.getValue();
            if (value instanceof String) {
                values.add(Inflector.toQuotedString(String.valueOf(value)));
            } else if (value == null) {
                values.add("NULL");
            } else {
                values.add(String.valueOf(value));
            }
            this.attrs.put(attr.getKey(), value);
        }
        return this;
    }
    
    private BaseModel model() throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Constructor<BaseModel> constructor = klass.getConstructor(Map.class);
        return constructor.newInstance(this.attrs);
    }
}
