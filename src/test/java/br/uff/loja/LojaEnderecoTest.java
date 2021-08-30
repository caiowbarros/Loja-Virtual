package br.uff.loja;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.uff.loja.core.dtos.EnderecoDTO;
import br.uff.loja.core.interfaces.services.IEnderecoService;
import br.uff.loja.core.interfaces.services.IUsuarioService;
import br.uff.loja.core.services.EnderecoService;
import br.uff.loja.core.services.UsuarioService;

public class LojaEnderecoTest {
    private IEnderecoService _enderecoService;
    private IUsuarioService _usuarioService;

    @Before
    public void setUp() throws Exception {
        _enderecoService = new EnderecoService();
        _usuarioService = new UsuarioService();
    }

    @Test
    public void testaInclusaoEndereco() throws Exception {
        Integer usuarioId = _usuarioService.listaUsuarios().get(0).getId();

        Integer qtdEnderecosAntesInsercao = _enderecoService.listaEnderecosPorUsuarioId(usuarioId).size();
        _enderecoService.insereEndereco(new EnderecoDTO("Casa", usuarioId, 24230322,
                "Avenida Almirante Ary Parreiras, 6", "Niterói", "RJ", "Brasil"));

        assertEquals(String.valueOf(qtdEnderecosAntesInsercao + 1),
                String.valueOf(_enderecoService.listaEnderecosPorUsuarioId(usuarioId).size()));
    }

    @Test
    public void testaUpdateEndereco() throws Exception {
        Integer usuarioId = 1;

        List<EnderecoDTO> enderecosDoUsuario = _enderecoService.listaEnderecosPorUsuarioId(usuarioId);

        if (enderecosDoUsuario.size() == 0) {
            this.testaInclusaoEndereco();
            enderecosDoUsuario = _enderecoService.listaEnderecosPorUsuarioId(usuarioId);
        }

        EnderecoDTO primeiroEndereco = enderecosDoUsuario.get(0);
        primeiroEndereco.setNome(primeiroEndereco.getNome() + "x");

        _enderecoService.atualizaEnderecoPorId(primeiroEndereco.getId(), primeiroEndereco);

        assertEquals(primeiroEndereco.toJson(),
                _enderecoService.encontraEnderecoPorId(primeiroEndereco.getId()).toJson());
    }

    @Test
    public void testaExclusaoEndereco() throws Exception {
        Integer usuarioId = _usuarioService.listaUsuarios().get(0).getId();
        List<EnderecoDTO> enderecosDoUsuario = _enderecoService.listaEnderecosPorUsuarioId(usuarioId);

        if (enderecosDoUsuario.size() <= 1) {
            this.testaInclusaoEndereco();
            enderecosDoUsuario = _enderecoService.listaEnderecosPorUsuarioId(usuarioId);
        }

        EnderecoDTO ultimoEndereco = enderecosDoUsuario.get(enderecosDoUsuario.size() - 1);

        _enderecoService.excluiEnderecoPorId(ultimoEndereco.getId());

        assertEquals(String.valueOf(enderecosDoUsuario.size() - 1),
                String.valueOf(_enderecoService.listaEnderecosPorUsuarioId(usuarioId).size()));
    }
}
