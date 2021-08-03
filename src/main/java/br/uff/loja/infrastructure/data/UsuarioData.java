package br.uff.loja.infrastructure.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.uff.loja.core.dtos.UsuarioDTO;
import br.uff.loja.core.enums.EPermissaoUsuario;
import br.uff.loja.core.exceptions.LojaException;
import br.uff.loja.core.interfaces.data.IUsuarioData;
import br.uff.loja.infrastructure.database.MySQLDAO;

public class UsuarioData implements IUsuarioData {
    private final MySQLDAO mysqlDAO;

    public UsuarioData() {
        this.mysqlDAO = new MySQLDAO();
    }

    @Override
    public UsuarioDTO encontraUsuarioPorId(Integer id) throws LojaException {
        try {
            Object[] bind = {id};
            List<HashMap<String, Object>> retorno = this.mysqlDAO.dbCarrega("SELECT id, name AS nome, email, password AS senha, role_id AS permissaoId FROM users WHERE id=?", bind);
            return new UsuarioDTO(retorno.get(0));
        } catch (Exception e) {
            throw new LojaException("Falha ao Recuperar o Usuário de id: " + id + ". (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

    @Override
    public List<UsuarioDTO> listaUsuarios() throws LojaException {
        try {
            Object[] bind = {};
            List<HashMap<String, Object>> retornoDesformatado = this.mysqlDAO.dbCarrega("SELECT id, name AS nome, email, password AS senha, role_id AS permissaoId FROM users", bind);
            List<UsuarioDTO> retornoFormatado = new ArrayList<>();
            retornoDesformatado.forEach(usuario -> retornoFormatado.add(new UsuarioDTO(usuario)));
            return retornoFormatado;
        } catch (Exception e) {
            throw new LojaException("Falha ao Listar Usuários. (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

    @Override
    public void atualizaUsuarioPorId(Integer id, UsuarioDTO usuario) throws LojaException {
        try {
            Object[] bind = {usuario.getNome(),usuario.getEmail(),usuario.getSenha(),usuario.getPermissaoId(),id};
            this.mysqlDAO.dbGrava("UPDATE users SET name=?,email=?,password=?,role_id=? WHERE id=?", bind, false);
        } catch (Exception e) {
            throw new LojaException("Falha ao Atualizar o Usuário de id: " + id + ". (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

    @Override
    public Integer encontraUsuarioIdPorEmailESenha(String email, String senha) throws LojaException {
        try {
            Object[] bind = {email,senha};
            return Integer.valueOf(String.valueOf(this.mysqlDAO.dbValor("id","users","email=? AND password=?", bind)));
        } catch (Exception e) {
            throw new LojaException("Falha ao Recuperar o Usuário de email: " + email + " e senha: " + senha + ". (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

    @Override
    public void insereUsuario(UsuarioDTO usuario) throws LojaException {
        try {
            Object[] bind = {usuario.getNome(),usuario.getEmail(),usuario.getSenha(),usuario.getPermissaoId()};
            this.mysqlDAO.dbGrava("INSERT INTO users (name,email,password,role_id) VALUES (?,?,?,?)", bind, false);
        } catch (Exception e) {
            throw new LojaException("Falha ao Inserir o Usuário. (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

    @Override
    public Boolean ehAdmin(Integer id) throws LojaException {
        try {
            Object[] bind = {id,EPermissaoUsuario.ADM.getId()};
            return Integer.valueOf(String.valueOf(this.mysqlDAO.dbValor("count(*)","users","id=? AND role_id=?", bind))) > 0;
        } catch (Exception e) {
            throw new LojaException("Falha ao Verificar se o Usuário de id: " + id + " é Admin. (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

    @Override
    public Boolean emailJaUsado(String email, String idPraIgnorar) throws LojaException {
        try {
            Object[] bind = {email,idPraIgnorar};
            return Integer.valueOf(String.valueOf(this.mysqlDAO.dbValor("count(*)","users","email=? AND id<>?", bind))) > 0;
        } catch (Exception e) {
            throw new LojaException("Falha ao Verificar se o email: " + email + " está em uso por um id que não seja o id: " + idPraIgnorar.toString() + ". (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }
    
}
