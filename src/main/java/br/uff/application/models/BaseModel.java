/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.application.models;

import br.uff.mutators.Evaluator;
import br.uff.mutators.Inflector;
import br.uff.sql.ConnectionManager;
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
    protected final Evaluator evaluator;
    
    public BaseModel(){
        this.evaluator = new Evaluator(this);
    }
    
    public BaseModel(Map<String, Object> attrs) {
        this.evaluator = new Evaluator(this);
        this.evaluator.initialize(attrs);
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
}
