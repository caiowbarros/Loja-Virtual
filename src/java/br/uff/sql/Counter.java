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
    public Counter(String tableName, Class klass) {
        super(tableName, klass);
        super.select("count(*) as c");
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
        PreparedStatement statement = connection.prepareStatement(super.build());
        ResultSet result = statement.executeQuery();
        if (!result.next()) return 0;
        super.reload();
        return result.getInt("c");
    }
}
