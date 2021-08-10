package br.uff.loja.core.interfaces.services;

import java.util.List;

import br.uff.loja.core.dtos.RoleDTO;
import br.uff.loja.core.exceptions.LojaException;

public interface IRoleService {
    public List<RoleDTO> listaRoles() throws LojaException;
}
