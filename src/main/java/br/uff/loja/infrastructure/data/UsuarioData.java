package br.uff.loja.infrastructure.data;

import java.util.List;

import br.uff.loja.core.dtos.UsuarioDTO;
import br.uff.loja.core.exceptions.LojaException;
import br.uff.loja.core.interfaces.data.IUsuarioData;

public class UsuarioData implements IUsuarioData {

    @Override
    public UsuarioDTO encontraUsuarioPorId(Integer id) throws LojaException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<UsuarioDTO> listaUsuarios() throws LojaException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void atualizaUsuarioPorId(Integer id, UsuarioDTO usuario) throws LojaException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Integer encontraUsuarioIdPorEmailESenha(String email, String senha) throws LojaException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void insereUsuario(UsuarioDTO usuario) throws LojaException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Boolean ehAdmin(Integer id) throws LojaException {
        // TODO Auto-generated method stub
        return null;
    }
    
}
