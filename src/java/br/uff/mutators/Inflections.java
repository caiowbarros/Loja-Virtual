/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.mutators;

import java.util.HashMap;

/**
 *
 * @author felipe
 */
public class Inflections {
    private static final HashMap<String, String> pluralizations = new HashMap() {{
        put("address", "address");
        put("category", "category");
    }};
    
    public static String fetchPlural(String key) {
        return pluralizations.get(key);
    }
}
