package br.uff.loja.core.interfaces.data;

import java.util.List;

import br.uff.loja.core.dtos.UsuarioDTO;
import br.uff.loja.core.exceptions.LojaException;

public interface IUsuarioData {
    public UsuarioDTO encontraUsuarioPorId(Integer id) throws LojaException;
    public List<UsuarioDTO> listaUsuarios() throws LojaException;
    public void atualizaUsuarioPorId(Integer id, UsuarioDTO usuario) throws LojaException;
    public Integer encontraUsuarioIdPorEmailESenha(String email, String senha) throws LojaException;
    public void insereUsuario(UsuarioDTO usuario) throws LojaException;
    public Boolean ehAdmin(Integer id) throws LojaException;
}
