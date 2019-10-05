/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.models;

import br.uff.exceptions.RecordAlreadyPersisted;
import br.uff.exceptions.RecordNotPersisted;
import br.uff.mutators.Evaluator;
import br.uff.mutators.Inflector;
import br.uff.sql.Counter;
import br.uff.sql.Selector;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
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
public class BaseModel {
    private static Class child = null;
    private static Connection connection = null;
    private static String tableName = null;
    protected final Evaluator evaluator;
    
    public BaseModel(){
        this.evaluator = new Evaluator(this);
    }
    
    public BaseModel(Map<String, Object> attrs) {
        this.evaluator = new Evaluator(this);
        this.evaluator.initialize(attrs);
    }
    
    public static Connection connect(Class klass) {
        
        if (connection != null) return connection;
        try {
            child = klass;
            tableName = getTableName();
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/devweb", "root", "");
             return connection;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(BaseModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static void disconnect() throws SQLException {
        connection.close();
    }
    
    public Object getAttribute(String attr) {
        Method method;
        try {
            String methodName = (String) Inflector.capitalize(attr);
            method = this.evaluator.getMethod("get" + methodName);
            return this.evaluator.invokeMethod(method);
        } catch (IllegalAccessException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(BaseModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public HashMap<String, Object> getAttributes() {
        HashMap<String, Object> attrs = new HashMap();
        Method[] methods = this.evaluator.getMethods();
        for (Method m : methods) {
            try {
                if (m.getName().startsWith("get")) {
                    String attrName = Inflector.retrieveAttrName(m.getName());
                    Object value = this.evaluator.invokeMethod(m);
                    attrs.put(attrName, value);
                }
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(BaseModel.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
        return attrs;
    }
    
    public boolean save() throws RecordAlreadyPersisted {
        if (this.getAttribute("id") != null) throw new RecordAlreadyPersisted();
        return commit(this);
    }
    
    public boolean update() throws RecordNotPersisted {
        int id = (int) this.getAttribute("id");
        if (this.getAttribute("id") == null) throw new RecordNotPersisted();
        HashMap<String, Object> where = new HashMap();
        where.put("id", id);
        return commitUpdate(this, where);
    }
    
    public static boolean commit(BaseModel model) {
        HashMap<String, Object> attrs = model.getAttributes();
        StringBuilder sql = new StringBuilder();
        sql.append("insert into ");
        sql.append(tableName);
        sql.append("(");
        int i = 1;
        for (String key : attrs.keySet()) {
            sql.append(Inflector.qf(key));
            sql.append(i == attrs.size() ? ")" : ",");
            i++;
        }
        i = 1;
        sql.append(" values(");
        for (Object value : attrs.values()) {
            sql.append(String.valueOf(value));
            sql.append(i == attrs.size() ? ")" : ",");
            i++;
        }
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(sql.toString());
            statement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(BaseModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static boolean commit(HashMap<String, Object> attrs) {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into ");
        sql.append(tableName);
        sql.append("(");
        int i = 1;
        for (String key : attrs.keySet()) {
            sql.append(Inflector.qf(key));
            sql.append(i == attrs.size() ? ")" : ",");
            i++;
        }
        i = 1;
        sql.append(" values(");
        for (Object value : attrs.values()) {
            sql.append(String.valueOf(value));
            sql.append(i == attrs.size() ? ")" : ",");
            i++;
        }
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(sql.toString());
            statement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(BaseModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static boolean commitUpdate(BaseModel model, HashMap<String, Object> where) {
        HashMap<String, Object> attrs = model.getAttributes();
        StringBuilder sql = new StringBuilder();
        sql.append("update ");
        sql.append(tableName);
        sql.append(" set ");
        int i = 1;
        for (Map.Entry pair : attrs.entrySet()) {
            sql.append(Inflector.qf(String.valueOf(pair.getKey())));
            sql.append(" = ");
            sql.append(String.valueOf(pair.getValue()));
            sql.append(i == attrs.size() ? " " : ",");
            i++;
        }
        i = 1;
        sql.append(" where ");
        for (Map.Entry pair : where.entrySet()) {
            sql.append(pair.getKey());
            sql.append(" = ");
            sql.append(String.valueOf(pair.getValue()));
            sql.append(i == where.size() ? "" : ",");
            i++;
        }
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(sql.toString());
            statement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(BaseModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static ArrayList<BaseModel> all() {
        return selector().run();
    }
    
    public static BaseModel find(int id) throws SQLException {
        ArrayList<BaseModel> result = selector().where("id = " + id).run();
        return result.get(0);
    }
    
    public static Selector select() {
        return selector();
    }
    
    public static Selector select(String sl) {
        return selector().select(sl);
    }
    
    public static BaseModel findBy(String condition) throws SQLException {
        BaseModel response = selector().where(condition).limit(1).run().get(0);
        return response;
    }
    
    public static int count() throws SQLException {
        return counter().count();
    }
    
    private static String getTableName() {
        String table = child.getSimpleName();
        table = Character.toLowerCase(table.charAt(0)) + table.substring(1);
        return table + "s";
    }
    
    private static Selector selector() {
        return new Selector(tableName, connection, child);
    }
    
    private static Counter counter() {
        return new Counter(tableName, connection, child);
    }
}
