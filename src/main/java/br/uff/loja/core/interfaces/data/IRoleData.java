package br.uff.loja.core.interfaces.data;

import java.util.List;

import br.uff.loja.core.dtos.RoleDTO;
import br.uff.loja.core.exceptions.LojaException;

public interface IRoleData {
    public List<RoleDTO> listaRoles() throws LojaException;
}
