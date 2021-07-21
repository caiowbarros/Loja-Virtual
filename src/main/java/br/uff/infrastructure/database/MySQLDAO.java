package br.uff.infrastructure.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class MySQLDAO {
    private Connection conn = null;

    public MySQLDAO()
            throws SQLException {
        this.conn = DriverManager.getConnection(dataSourceUrl, dataSourceUsername, dataSourcePassword);
    }

    public void dbTransaction(String[] comandos, Object[][] bind) throws SQLException {
        try {
            this.conn.setAutoCommit(false);
            Integer aux = 0;
            for (String comando : comandos) {
                // pega comando e verifica se index existe e pega bind
                this.dbGrava(comando, aux < bind.length ? bind[aux] : null, true);
                // incrementa aux
                aux += 1;
            }
            this.conn.commit();
        } catch (SQLException ex) {
            this.conn.rollback();
            throw new SQLException(ex.getMessage());
        } finally {
            this.fechaConexao();
            this.conn.setAutoCommit(true);
        }
    }

    public void dbGrava(String comando, Object[] bind, Boolean ehTransacao) throws SQLException {
        PreparedStatement sql = this.montaComando(comando, bind);
        sql.executeUpdate();
        this.fechaComando(sql);
        if (ehTransacao) {
            this.fechaConexao();
        }
    }

    public Object dbValor(String campo, String tabela, String filtro, Object[] bind) throws SQLException {
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
        ArrayList<HashMap<String, Object>> ret = this.transformResultSetInArrayListHashMap(resultado);
        this.fechaComando(sql);
        if (ret.size() > 0) {
            HashMap<String, Object> row = ret.get(0);
            // pega primeira coluna da primeira row
            return row.values().toArray()[0];

        }
        return null;
    }

    public ArrayList<HashMap<String, Object>> dbCarrega(String consulta, Object[] bind) throws SQLException {
        PreparedStatement sql = this.montaComando(consulta, bind);
        ResultSet rs = sql.executeQuery();
        ArrayList<HashMap<String, Object>> ret = this.transformResultSetInArrayListHashMap(rs);
        this.fechaComando(sql);
        this.fechaConexao();
        return ret;
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
    public PreparedStatement montaComando(String sql, Object[] bind) throws SQLException {
        PreparedStatement comando;
        // inicia comando
        comando = this.conn.prepareStatement(sql);
        // retorna comando com bind inserido
        if (bind != null) {
            comando = this.setBind(comando, bind);
        }
        return comando;
    }

    public void fechaConexao() throws SQLException {
        if (!this.conn.isClosed()) {
            this.conn.close();
        }
    }

    public void fechaComando(PreparedStatement comando) throws SQLException {
        if (!comando.isClosed()) {
            comando.close();
        }
    }

    public String pegaDbNome() throws SQLException {
        return this.conn.getMetaData().getDatabaseProductName();
    }

    public PreparedStatement setBind(PreparedStatement comando, Object[] bind) throws SQLException {
        // define contador
        Integer contador = 1;
        for (Object valor : bind) {
            // insere valor no Statement
            comando.setObject(contador, valor);
            // incrementa contador
            contador += 1;
        }
        // retorna comando com o bind definido
        return comando;
    }

    public ArrayList<HashMap<String, Object>> transformResultSetInArrayListHashMap(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        while (rs.next()) {
            HashMap<String, Object> row = new HashMap<>(columns);
            for (int i = 1; i <= columns; ++i) {
                row.put(md.getColumnName(i), rs.getObject(i));
            }
            list.add(row);
        }
        return list;
    }
}
