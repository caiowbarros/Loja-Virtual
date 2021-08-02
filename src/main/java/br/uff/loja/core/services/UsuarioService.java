package br.uff.loja.core.services;

import java.util.List;

import br.uff.loja.core.dtos.UsuarioDTO;
import br.uff.loja.core.exceptions.LojaException;
import br.uff.loja.core.interfaces.services.IUsuarioService;

public class UsuarioService implements IUsuarioService {

    @Override
    public UsuarioDTO login(String email, String senha) throws LojaException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UsuarioDTO gravaUsuario(UsuarioDTO usuario) throws LojaException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<UsuarioDTO> listaUsuarios() throws LojaException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UsuarioDTO encontraUsuarioPorId(Integer id) throws LojaException {
        // TODO Auto-generated method stub
        return null;
    }
    
}
