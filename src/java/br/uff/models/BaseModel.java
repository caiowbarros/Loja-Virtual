/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.models;

import br.uff.mutators.Evaluator;
import br.uff.mutators.Inflector;
import br.uff.sql.Destroyer;
import br.uff.sql.SqlManager;
import br.uff.sql.Inserter;
import br.uff.sql.Selector;
import br.uff.sql.Updater;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
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
    private static SqlManager query;
    
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
            tableName = Inflector.classToTable(child);
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/devweb", "root", "");
            query = new SqlManager(tableName, connection, child);
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
    
    public boolean save() {
        Object id = this.getAttribute("id");
        if (id == null) return commit(this);
        return commitUpdate(this);
    }
    public static boolean commit(BaseModel model) {
        HashMap<String, Object> attrs = model.getAttributes();
        try {
            insert().values(attrs).run();
            return true;
        } catch (SQLException | NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(BaseModel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public static boolean commit(HashMap<String, Object> attrs) {
        try {
            insert().values(attrs).run();
            return true;
        } catch (SQLException | NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(BaseModel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public static boolean commitUpdate(BaseModel model) {
        HashMap<String, Object> attrs = model.getAttributes();
        try {
            update().set(attrs).run();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(BaseModel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public static boolean commitUpdate(HashMap<String, Object> attrs) {
        try {
            update().set(attrs).run();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(BaseModel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public static ArrayList<BaseModel> all() throws SQLException {
        return select().run();
    }
    
    public static BaseModel find(int id) throws SQLException {
        ArrayList<BaseModel> queryResults = select().where("id = " + id).run();
        if(queryResults.isEmpty()) return null;
        return queryResults.get(0);
    }
    
    public static BaseModel findBy(String condition) throws SQLException {
        ArrayList<BaseModel> queryResults = select().where(condition).limit(1).run();
        if(queryResults.isEmpty()) return null;
        return queryResults.get(0);
    }
    
    /*
    * Delegated Methods
    */
    public static Selector select() {
        return query.select();
    }
    
    public static Selector select(String str) {
        return query.select(str);
    }
    
    public static int count() throws SQLException {
        return query.count();
    }
    
    public static Inserter insert() {
        return query.insert();
    }
    
    public static Updater update() {
        return query.update();
    }
    
    public static Destroyer delete() {
        return query.delete();
    }
}
