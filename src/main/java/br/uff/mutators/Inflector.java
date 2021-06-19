/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.mutators;

import java.util.ArrayList;

/**
 *
 * @author felipe
 */
public class Inflector {
    public static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
    
    public static String downsize(String str) {
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }
    
    public static String toSetter(String str) {
        String[] splited = str.split("_");
        String response = "";
        for (String word : splited) {
            response += capitalize(word);
        }
        return response;
    }
    
    public static String qf(String str) {
        String[] words = str.split("(?=\\p{Lu})");
        String response = "";
        for (int i = 0; i < words.length; i++ ) {
            response = response + downsize(words[i]);
            if (i != words.length - 1) response = response + "_";
        }
        return response;
    }
    
    public static String toAttribute(String str) {
        return downsize(toSetter(str));
    }
    
    public static String toQuotedString(String str) {
        return "'" + str + "'";
    }
    
    public static String retrieveAttrName(String method) {
        String response = method;
        if (method.startsWith("set")) response = method.replaceFirst("set", "");
        if (method.startsWith("get")) response = method.replaceFirst("get", "");
        return response;
    }
    
    public static String joins(ArrayList<String> arr) {
        String str = arr.toString();
        str = str.substring(1, str.length() - 1);
        return str.replace(',', ' ');
    }
    
    public static String joins(ArrayList<String> arr, char separator) {
        String str = arr.toString();
        str = str.substring(1, str.length() - 1);
        return str.replace(',', separator);
    }
    
    public static String pluralize(String str) {
        String plural = Inflections.fetchPlural(str);
        if (plural == null) return str + "s";
        return plural;
    }
    
    public static String classToTable(Class klass) {
        String table = klass.getSimpleName();
        table = downsize(table);
        return pluralize(table);
    }
}
