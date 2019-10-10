/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.sql;

import br.uff.mutators.Inflector;
import java.sql.SQLException;

/**
 *
 * @author felipe
 */
public class SqlManager {
    private final Getter getter;
    private final Setter setter;
    private final Destroyer destroyer;
    
    public SqlManager(Class klass) {
        String tableName = Inflector.classToTable(klass);
        getter = new Getter(tableName, klass);
        setter = new Setter(tableName, klass);
        destroyer = new Destroyer(tableName);
    }
    
    public Selector select() {
        return getter.select();
    }
    
    public Selector select(String str) {
        return getter.select(str);
    }
    
    public int count() throws SQLException {
        return getter.count();
    }
    
    public Inserter insert() {
        return setter.insert();
    }
    
    public Updater update() {
        return setter.update();
    }
    
    public Destroyer delete() {
        return destroyer;
    }
}
