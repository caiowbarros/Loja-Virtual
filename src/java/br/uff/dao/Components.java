/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author HP
 */
public class Components {

    public String mostraSelect(String nameSelect, boolean required, String consulta, String[] bind, String selectedValue, String cssclass, String style) throws SQLException {
        MySql db = null;
        String html = null;
        html = "<select style=\"" + style + "\" class=\"" + nameSelect + "\" name=\"" + nameSelect + "\" " + (required == true ? " required " : "") + ">";
        try {
            db = new MySql("test", "root", "");
            ResultSet ret = db.dbCarrega(consulta, bind);
            while (ret.next()) {
                html += "<option value=\"" + ret.getString("value") + "\" " + (selectedValue.equals(ret.getString("value")) ? " selected " : "") + ">" + ret.getString("text") + "</option>";
            }
        } catch (ClassNotFoundException | SQLException ex) {
            html += "<option value=\"err\">" + ex.getMessage() + "</option>";
        } finally {
            db.destroyDb();
        }
        html += "</select>";
        return html;
    }
}
