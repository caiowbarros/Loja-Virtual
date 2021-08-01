package br.uff.loja.core.interfaces.services;

import br.uff.loja.core.dtos.UsuarioDTO;
import br.uff.loja.core.exceptions.LojaException;

public interface IUsuarioService {
    public UsuarioDTO login(String email, String senha) throws LojaException;
    public UsuarioDTO gravaUsuario(UsuarioDTO usuario) throws LojaException;
}
