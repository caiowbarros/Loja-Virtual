/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.sql;

import br.uff.models.BaseModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author felipe
 */
public class Counter extends Selector {
    private boolean isMemoized;
    private int amount;
    
    public Counter(String tableName, Connection connection, Class child) {
        super(tableName, connection, child);
        super.select("count(*) as c");
        this.isMemoized = false;
        this.amount = 0;
    }
    
    @Override
    public ArrayList<BaseModel> run() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public Selector select(String select) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
    
    public int count() throws SQLException {
        return getCount();
    }
    
    public int size() throws SQLException {
        if (this.isMemoized == true) return this.amount;
        this.amount = getCount();
        this.isMemoized = true;
        return this.amount;
    }
    
    private int getCount() throws SQLException {
        PreparedStatement statement = connection.prepareStatement(super.build());
        ResultSet result = statement.executeQuery();
        if (result.next()) return result.getInt("c");
        return 0;
    }
}
