package br.uff.loja;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.uff.loja.core.dtos.UsuarioDTO;
import br.uff.loja.core.enums.EPermissaoUsuario;
import br.uff.loja.core.interfaces.services.IUsuarioService;
import br.uff.loja.core.services.UsuarioService;

public class LojaUsuarioTest {
    private IUsuarioService _usuarioService;

    @Before
    public void setUp() throws Exception {
        _usuarioService = new UsuarioService();
    }

    @Test
    public void testaLoginErro() throws Exception {
        String msgErr = "";

        try {
            _usuarioService.login("email@errado.com", "xxx");
        } catch (Exception e) {
            msgErr = e.getMessage();
        }

        assertEquals("Credenciais inválidas, por favor verifique os dados imputados e tente novamente!", msgErr);
    }

    @Test
    public void testaLoginSucesso() throws Exception {
        UsuarioDTO usuario = _usuarioService.listaUsuarios().get(0);

        UsuarioDTO usuarioLogin = _usuarioService.login(usuario.getEmail(), usuario.getSenha());

        assertEquals(usuario.toJson(), usuarioLogin.toJson());
    }

    @Test
    public void testaUpdateUsuario() throws Exception {
        List<UsuarioDTO> usuarios = _usuarioService.listaUsuarios();
        UsuarioDTO primeiroUsuario = usuarios.get(0);
        primeiroUsuario.setNome(primeiroUsuario.getNome() + "x");

        UsuarioDTO usuarioAtualizado = _usuarioService.gravaUsuario(primeiroUsuario);

        assertEquals(primeiroUsuario.toJson(),
                _usuarioService.encontraUsuarioPorId(usuarioAtualizado.getId()).toJson());
    }

    @Test
    public void testaInsercaoUsuario() throws Exception {
        UsuarioDTO novoUsuario = new UsuarioDTO(null, "Teste Insert User", "testando@example.com" + Math.random(),
                "123@SeiQueEhUmaSenhaRuim", EPermissaoUsuario.CLIENTE.getId());

        UsuarioDTO usuarioGravado = _usuarioService.gravaUsuario(novoUsuario);

        // setando id pra bater igualzinho apos insert
        novoUsuario.setId(usuarioGravado.getId());

        assertEquals(novoUsuario.toJson(), usuarioGravado.toJson());
    }
}
