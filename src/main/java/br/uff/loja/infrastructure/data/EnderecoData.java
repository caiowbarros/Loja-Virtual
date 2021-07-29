package br.uff.loja.infrastructure.data;

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
            return new EnderecoDTO(retorno.get(0));
        } catch (Exception e) {
            throw new Exception("Falha ao Recuperar o Endereço de id: " + id + ". (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

    @Override
    public Integer excluiEnderecoPorId(Integer id) throws Exception {
        try {
            Object[] bind = {id};
            return this.mysqlDAO.dbGrava("DELETE FROM address WHERE id=?", bind, false);
        } catch (Exception e) {
            throw new Exception("Falha ao Excluir o Endereço de id: " + id + ". (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

    @Override
    public Integer atualizaEnderecoPorId(Integer id, EnderecoDTO endereco) throws Exception {
        try {
            Object[] bind = {endereco.nome,endereco.cep,endereco.logradouro,endereco.cidade,endereco.estado,endereco.cidade,endereco.id};
            return this.mysqlDAO.dbGrava("UPDATE address SET name=?, zipcode=?, address=?, city=?, state=?, country=? WHERE id=?", bind, false);
        } catch (Exception e) {
            throw new Exception("Falha ao Atualizar o Endereço de id: " + id + ". (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

    @Override
    public Integer insereEndereco(EnderecoDTO endereco) throws Exception {
        try {
            Object[] bind = {endereco.nome,endereco.usuarioId,endereco.cep,endereco.logradouro,endereco.cidade,endereco.estado,endereco.cidade};
            return this.mysqlDAO.dbGrava("INSERT INTO address (name,user_id,zipcode,address,city,state,country) VALUES (?,?,?,?,?,?,?)", bind, false);
        } catch (Exception e) {
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
                retornoFormatado.add(new EnderecoDTO(endereco));
            });
            
            return retornoFormatado;
        } catch (Exception e) {
            throw new Exception("Falha a Lista de Endereços para o usuário de id: " + usuarioId + ". (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }
    
}
