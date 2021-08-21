package br.uff.loja.core.interfaces.services;

import java.util.List;

import br.uff.loja.core.dtos.UsuarioDTO;
import br.uff.loja.core.exceptions.LojaException;

public interface IUsuarioService {

    public UsuarioDTO login(String email, String senha) throws LojaException;

    public UsuarioDTO gravaUsuario(UsuarioDTO usuario) throws LojaException;

    public List<UsuarioDTO> listaUsuarios() throws LojaException;

    public UsuarioDTO encontraUsuarioPorId(Integer id) throws LojaException;
}
