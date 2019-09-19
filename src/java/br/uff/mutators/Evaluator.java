/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.mutators;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author felipe
 */
public class Evaluator {
    Object obj;
    Class klass;
    
    public Evaluator(Object obj) {
        this.obj = obj;
        this.klass = obj.getClass();
    }
    
    public void initialize(Map<String, Object> attributes) {
        attributes.entrySet().forEach((pair) -> {
            String key = (String) pair.getKey();
            String attrSetter = "set" + Inflector.toSetter(key);
            try {
                Class type = pair.getValue().getClass();
                Method method = this.getMethod(attrSetter, Evaluator.toPrimitive(type));
                this.invokeMethod(method, pair.getValue());
            } catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(Evaluator.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    public Method getMethod(String name) throws NoSuchMethodException {
        return klass.getDeclaredMethod(name, null);
    }
    
    public Method getMethod(String name, Class... types) throws NoSuchMethodException {
        return klass.getDeclaredMethod(name, types);
    }
    
    public Method[] getMethods() {
        return klass.getDeclaredMethods();
    }
    
    public Object invokeMethod(Method method) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return method.invoke(this.obj);
    }
    
    public Object invokeMethod(Method method, Object args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return method.invoke(this.obj, args);
    }
    
    public Object invokeMethod(Method method, Object[]... args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return method.invoke(this.obj, (Object[]) args);
    }
    
    public static Class toPrimitive(Class klass) {
        if (klass == Integer.class) {
            return int.class;
        }
        return klass;
    }
}
