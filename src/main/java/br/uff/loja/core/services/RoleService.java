package br.uff.loja.core.services;

import java.util.List;

import br.uff.loja.core.dtos.RoleDTO;
import br.uff.loja.core.exceptions.LojaException;
import br.uff.loja.core.interfaces.data.IRoleData;
import br.uff.loja.core.interfaces.services.IRoleService;
import br.uff.loja.infrastructure.data.RoleData;

public class RoleService implements IRoleService {
    private IRoleData roleData;

    public RoleService() {
        roleData = new RoleData();
    }

    @Override
    public List<RoleDTO> listaRoles() throws LojaException {
        return roleData.listaRoles();
    }
    
}
