package br.uff.loja.infrastructure.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.uff.loja.core.dtos.RoleDTO;
import br.uff.loja.core.exceptions.LojaException;
import br.uff.loja.core.interfaces.data.IRoleData;
import br.uff.loja.infrastructure.database.MySQLDAO;

public class RoleData implements IRoleData {

    private final MySQLDAO mysqlDAO;

    public RoleData() {
        this.mysqlDAO = new MySQLDAO();
    }

    @Override
    public List<RoleDTO> listaRoles() throws LojaException {
        try {
            Object[] bind = {};
            List<HashMap<String, Object>> retornoDesformatado = this.mysqlDAO.dbCarrega("SELECT id, name AS nome FROM roles", bind);
            List<RoleDTO> retornoFormatado = new ArrayList<>();
            retornoDesformatado.forEach(role -> retornoFormatado.add(new RoleDTO(role)));
            return retornoFormatado;
        } catch (Exception e) {
            throw new LojaException("Falha ao listar Roles. (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

}
