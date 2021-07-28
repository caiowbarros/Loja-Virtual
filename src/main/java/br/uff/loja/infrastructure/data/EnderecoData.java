package br.uff.loja.infrastructure.data;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.uff.loja.core.dtos.EnderecoDTO;
import br.uff.loja.core.interfaces.data.IEnderecoData;
import br.uff.loja.infrastructure.database.MySQLDAO;

public class EnderecoData implements IEnderecoData {
    private final MySQLDAO mysqlDAO;

    public EnderecoData() {
        this.mysqlDAO = new MySQLDAO();
    }

    @Override
    public EnderecoDTO encontraEnderecoPorId(Integer id) throws Exception {
        try {
            Object[] bind = {id};
            ArrayList<HashMap<String, Object>> retorno = this.mysqlDAO.dbCarrega("SELECT id, name AS nome, user_id AS usuarioId, zipcode AS cep, address AS logradouro, city AS cidade, state AS estado, country AS pais FROM address WHERE id=?", bind);
            EnderecoDTO retornoFormatado = new EnderecoDTO();
            retornoFormatado.id = Integer.valueOf(String.valueOf(retorno.get(0).get("id")));
            retornoFormatado.usuarioId = Integer.valueOf(String.valueOf(retorno.get(0).get("usuarioId")));
            retornoFormatado.nome = String.valueOf(retorno.get(0).get("nome"));
            retornoFormatado.cep = String.valueOf(retorno.get(0).get("cep"));
            retornoFormatado.logradouro = String.valueOf(retorno.get(0).get("logradouro"));
            retornoFormatado.estado = String.valueOf(retorno.get(0).get("estado"));
            retornoFormatado.cidade = String.valueOf(retorno.get(0).get("cidade"));
            retornoFormatado.pais = String.valueOf(retorno.get(0).get("pais"));
            return retornoFormatado;
        } catch (SQLException e) {
            throw new Exception("Falha ao Recuperar o Endereço de id: " + id + ". (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

    @Override
    public Integer excluiEnderecoPorId(Integer id) throws Exception {
        try {
            Object[] bind = {id};
            return this.mysqlDAO.dbGrava("DELETE address WHERE id=?", bind, false);
        } catch (SQLException e) {
            throw new Exception("Falha ao Excluir o Endereço de id: " + id + ". (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

    @Override
    public Integer atualizaEnderecoPorId(Integer id, EnderecoDTO endereco) throws Exception {
        try {
            Object[] bind = {endereco.nome,endereco.cep,endereco.logradouro,endereco.cidade,endereco.estado,endereco.cidade};
            return this.mysqlDAO.dbGrava("UPDATE address SET name=?, zipcode=?, address=?, city=?, state=?, country=? WHERE id=?", bind, false);
        } catch (SQLException e) {
            throw new Exception("Falha ao Atualizar o Endereço de id: " + id + ". (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

    @Override
    public Integer insereEndereco(EnderecoDTO endereco) throws Exception {
        try {
            Object[] bind = {endereco.nome,endereco.usuarioId,endereco.cep,endereco.logradouro,endereco.cidade,endereco.estado,endereco.cidade};
            return this.mysqlDAO.dbGrava("INSERT INTO address (name,user_id,zipcode,address,city,state,country) VALUES (?,?,?,?,?,?,?) WHERE id=?", bind, false);
        } catch (SQLException e) {
            throw new Exception("Falha ao Inserir o Endereço de para o Usuário de id: " + endereco.usuarioId + ". (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

    @Override
    public List<EnderecoDTO> listaEnderecosPorUsuarioId(Integer usuarioId) throws Exception {
        try {
            Object[] bind = {usuarioId};
            ArrayList<HashMap<String, Object>> retornoDesformatado = this.mysqlDAO.dbCarrega("SELECT id, name AS nome, user_id AS usuarioId, zipcode AS cep, address AS logradouro, city AS cidade, state AS estado, country AS pais FROM address WHERE user_id=?", bind);
            ArrayList<EnderecoDTO> retornoFormatado = new ArrayList<EnderecoDTO>();
            retornoDesformatado.forEach((endereco) -> {
                EnderecoDTO enderecoFormatado = new EnderecoDTO();
                enderecoFormatado.id = Integer.valueOf(String.valueOf(endereco.get("id")));
                enderecoFormatado.usuarioId = Integer.valueOf(String.valueOf(endereco.get("usuarioId")));
                enderecoFormatado.nome = String.valueOf(endereco.get("nome"));
                enderecoFormatado.cep = String.valueOf(endereco.get("cep"));
                enderecoFormatado.logradouro = String.valueOf(endereco.get("logradouro"));
                enderecoFormatado.estado = String.valueOf(endereco.get("estado"));
                enderecoFormatado.cidade = String.valueOf(endereco.get("cidade"));
                enderecoFormatado.pais = String.valueOf(endereco.get("pais"));
                retornoFormatado.add(enderecoFormatado);
            });
            
            return retornoFormatado;
        } catch (SQLException e) {
            throw new Exception("Falha a Lista de Endereços para o usuário de id: " + usuarioId + ". (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }
    
}
