package br.uff.loja.core.services;

import java.util.List;

import br.uff.loja.core.dtos.UsuarioDTO;
import br.uff.loja.core.exceptions.LojaException;
import br.uff.loja.core.interfaces.data.IUsuarioData;
import br.uff.loja.core.interfaces.services.IUsuarioService;
import br.uff.loja.infrastructure.data.UsuarioData;

public class UsuarioService implements IUsuarioService {

    private IUsuarioData usuarioData;

    public UsuarioService() {
        usuarioData = new UsuarioData();
    }

    @Override
    public UsuarioDTO login(String email, String senha) throws LojaException {
        try {
            return usuarioData.encontraUsuarioPorId(usuarioData.encontraUsuarioIdPorEmailESenha(email, senha));
        } catch (Exception e) {
            throw new LojaException("Credenciais inválidas, por favor verifique os dados imputados e tente novamente!");
        }
    }

    @Override
    public UsuarioDTO gravaUsuario(UsuarioDTO usuario) throws LojaException {
        if (Boolean.TRUE.equals(usuarioData.emailJaUsado(usuario.getEmail(), String.valueOf(usuario.getId())))) {
            throw new LojaException("O e-mail " + usuario.getEmail() + " já está sendo usado por outro usuário...");
        }

        if (usuario.getId() != null) {
            usuarioData.atualizaUsuarioPorId(usuario.getId(), usuario);
        } else {
            usuarioData.insereUsuario(usuario);
        }

        return usuarioData.encontraUsuarioPorId(
                usuarioData.encontraUsuarioIdPorEmailESenha(usuario.getEmail(), usuario.getSenha()));
    }

    @Override
    public List<UsuarioDTO> listaUsuarios() throws LojaException {
        return usuarioData.listaUsuarios();
    }

    @Override
    public UsuarioDTO encontraUsuarioPorId(Integer id) throws LojaException {
        return usuarioData.encontraUsuarioPorId(id);
    }

    @Override
    public Boolean ehAdmin(Integer id) throws LojaException {
        return usuarioData.ehAdmin(id);
    }

}
