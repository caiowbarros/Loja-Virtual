/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author HP
 */
public class MySql {

    private Connection conn = null;

    public MySql() throws SQLException, ClassNotFoundException {
        this.initDbMySql("e_store_mysql", "3306", "e_store", "e_store", "123456");
    }

    public void dbTransaction(String[] comandos, String[][] bind) throws SQLException {
        try {
            this.conn.setAutoCommit(false);
            Integer aux = 0;
            for (String comando : comandos) {
                // pega comando e verifica se index existe e pega bind
                this.dbGrava(comando, aux < bind.length ? bind[aux] : null);
                // incrementa aux
                aux += 1;
            }
            this.conn.commit();
        } catch (SQLException ex) {
            this.conn.rollback();
            throw new SQLException(ex.getMessage());
        } finally {
            this.conn.setAutoCommit(true);
        }
    }

    public void dbGrava(String comando, String[] bind) throws SQLException {
        PreparedStatement sql = this.montaComando(comando, bind);
        sql.executeUpdate();
        sql = this.fechaComando(sql);
    }

    public String dbValor(String campo, String tabela, String filtro, String[] bind) throws SQLException {
        if (!tabela.toUpperCase().startsWith("SELECT")) {
            tabela = "SELECT * FROM " + tabela;
        }
        if (!filtro.equals("")) {
            tabela = "SELECT * FROM (" + tabela + ") _x1 WHERE " + filtro;
        }
        String consulta = "SELECT " + campo + " FROM (" + tabela + ") _x2";

        PreparedStatement sql;
        sql = this.montaComando(consulta, bind);
        ResultSet resultado = sql.executeQuery();
        if (resultado.next()) {
            return resultado.getString(campo);
        } else {
            return null;
        }
    }

    public ResultSet dbCarrega(String consulta, String[] bind) throws SQLException {
        PreparedStatement sql = this.montaComando(consulta, bind);
        ResultSet rs = sql.executeQuery();
        return rs;
    }

    private void initDbMySql(String host, String port, String dataBase, String nomeUsuario, String senhaUsuario)
            throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + dataBase, nomeUsuario, senhaUsuario);
    }

    public void destroyDb() throws SQLException {
        this.conn.close();
    }

    /**
     *
     * @param sql
     * @param bind
     * @return
     * @throws java.sql.SQLException
     */
    public PreparedStatement montaComando(String sql, String[] bind) throws SQLException {
        PreparedStatement comando;
        // inicia comando
        comando = this.conn.prepareStatement(sql);
        // retorna comando com bind inserido
        if (bind != null) {
            comando = this.setBind(comando, bind);
        }
        return comando;
    }

    public PreparedStatement fechaComando(PreparedStatement comando) throws SQLException {
        comando.close();
        return comando;
    }

    public PreparedStatement setBind(PreparedStatement comando, String[] bind) throws SQLException {
        // define contador
        Integer contador = 1;
        for (String valor : bind) {
            // insere valor no Statement
            comando.setString(contador, valor);
            // incrementa contador
            contador += 1;
        }
        // retorna comando com o bind definido
        return comando;

    }

}
