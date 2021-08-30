package br.uff.loja;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.uff.loja.core.dtos.AvaliacaoProdutoInsertDTO;
import br.uff.loja.core.interfaces.services.IAvaliacaoService;
import br.uff.loja.core.interfaces.services.IProdutoService;
import br.uff.loja.core.interfaces.services.IUsuarioService;
import br.uff.loja.core.services.AvaliacaoService;
import br.uff.loja.core.services.ProdutoService;
import br.uff.loja.core.services.UsuarioService;

public class LojaAvaliacaoTest {
    private IAvaliacaoService _avaliacaoService;
    private IProdutoService _produtoService;
    private IUsuarioService _usuarioService;

    @Before
    public void setUp() throws Exception {
        _avaliacaoService = new AvaliacaoService();
        _produtoService = new ProdutoService();
        _usuarioService = new UsuarioService();
    }

    @Test
    public void testaAvaliacaoDuplicada() throws Exception {
        String exMessage = "";

        Integer produtoId = _produtoService.listaProdutosAdm().get(0).getId();
        Integer usuarioId = _usuarioService.listaUsuarios().get(0).getId();

        try {
            AvaliacaoProdutoInsertDTO avaliacaoProdutoInsertDTO = new AvaliacaoProdutoInsertDTO(usuarioId, produtoId, 4,
                    "Testando descrição!", "Tô testando pois se gravar 2 vezes deu ruim!");

            _avaliacaoService.avaliaProduto(avaliacaoProdutoInsertDTO);
            _avaliacaoService.avaliaProduto(avaliacaoProdutoInsertDTO);
        } catch (Exception ex) {
            exMessage = ex.getMessage();
        }

        if (_avaliacaoService.recuperaAvaliacoesDeUmProduto(produtoId).isEmpty()) {
            throw new Exception("A avaliação para o produto de id: " + produtoId + ", não foi realizada!");
        }

        assertEquals("O produto de id: " + produtoId + " já foi avaliado!", exMessage);
    }
}
