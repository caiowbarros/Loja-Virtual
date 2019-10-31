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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    
    public Inserter(String tableName, Class klass) {
        this.reload();
        this.insert = "insert into " + tableName;
        this.klass = klass;
        this.connection = ConnectionManager.getConnection();
    }
    
    protected void reload() {
        this.columns = new ArrayList();
        this.values = new ArrayList();
        this.attrs = new HashMap();
    }
    
    public BaseModel run() throws SQLException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        PreparedStatement statement = connection.prepareStatement(this.build(), Statement.RETURN_GENERATED_KEYS);
        statement.executeUpdate();
        int id = -1;
        ResultSet rs = statement.getGeneratedKeys();
        if(rs.next()) id = rs.getInt(1);
        if(id < 0) throw new SQLException();
        this.attrs.put("id", id);
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
    
    public Inserter values(Map.Entry<String,Object>... attrs ) {
        for(Map.Entry<String,Object> attr : attrs) {
            addAttr(attr);
        }
        return this;
    }
    
    public Inserter values(HashMap<String, Object> attrs) {
        attrs.entrySet().forEach((attr) -> {
            addAttr(attr);
        });
        return this;
    }
    
    private void addAttr(Map.Entry<String, Object> attr) {
        columns.add(attr.getKey());
        Object value = attr.getValue();
        if (value instanceof String && !value.equals("SYSDATE()")) {
            values.add(Inflector.toQuotedString(String.valueOf(value)));
        } else if (value == null) {
            values.add("NULL");
        } else {
            values.add(String.valueOf(value));
        }
        this.attrs.put(attr.getKey(), value);
    }
    
    private BaseModel model() throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Constructor<BaseModel> constructor = klass.getConstructor(Map.class);
        return constructor.newInstance(this.attrs);
    }
}
