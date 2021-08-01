package br.uff.loja.core.interfaces.data;

import br.uff.loja.core.dtos.UsuarioDTO;
import br.uff.loja.core.exceptions.LojaException;

public interface IUsuarioData {
    public UsuarioDTO encontraUsuarioPorId(Integer id) throws LojaException;
    public void atualizaUsuarioPorId(Integer id, UsuarioDTO usuario) throws LojaException;
    public Integer encontraUsuarioIdPorEmailESenha(String email, String senha) throws LojaException;
    public void insereUsuario(UsuarioDTO usuario) throws LojaException;
}
