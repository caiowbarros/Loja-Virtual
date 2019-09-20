/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.mutators;

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
}
