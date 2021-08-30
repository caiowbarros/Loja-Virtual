package br.uff.loja;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.uff.loja.core.dtos.FiltraProdutoDTO;
import br.uff.loja.core.dtos.PaginateDTO;
import br.uff.loja.core.dtos.ProdutoDTO;
import br.uff.loja.core.dtos.ProdutoListaDTO;
import br.uff.loja.core.enums.EProdutoCategoria;
import br.uff.loja.core.interfaces.services.IProdutoService;
import br.uff.loja.core.interfaces.services.IUsuarioService;
import br.uff.loja.core.services.ProdutoService;
import br.uff.loja.core.services.UsuarioService;

public class LojaProdutoTest {
    private IProdutoService _produtoService;
    private IUsuarioService _usuarioService;

    @Before
    public void setUp() throws Exception {
        _produtoService = new ProdutoService();
        _usuarioService = new UsuarioService();
    }

    @Test
    public void testaInclusaoProduto() throws Exception {
        Integer qtdProdutosAntesInsercao = _produtoService.listaProdutosAdm().size();
        _produtoService.insereProduto(new ProdutoDTO(null, "Playstation 6", 2000000.55, "Super caro!",
                "url de uma imagem", EProdutoCategoria.PLAYSTATIONCONSOLES.getId(), 1));

        assertEquals(String.valueOf(qtdProdutosAntesInsercao + 1),
                String.valueOf(_produtoService.listaProdutosAdm().size()));
    }

    @Test
    public void testaListaProdutosVitrine() throws Exception {
        Integer paginaAtual = 0;
        Integer ultimaPagina = 1;

        Double precoMinimo = 20.1;
        Double precoMaximo = 28.8;

        List<ProdutoDTO> produtosAdmLista = _produtoService.listaProdutosAdm();

        produtosAdmLista.removeIf(filter -> filter.getPreco() < precoMinimo || filter.getPreco() > precoMaximo);

        FiltraProdutoDTO filtro = new FiltraProdutoDTO();
        filtro.setItensPorPagina(5);
        filtro.setPrecoMinimo(precoMinimo);
        filtro.setPrecoMaximo(precoMaximo);

        List<ProdutoListaDTO> produtosDoPaginate = new ArrayList<>();

        while (ultimaPagina > paginaAtual) {
            paginaAtual += 1;
            filtro.setPaginaAtual(paginaAtual);

            PaginateDTO<List<ProdutoListaDTO>> produtosFiltrado = _produtoService.listaProdutosVitrine(filtro);

            produtosDoPaginate.addAll(produtosFiltrado.getDados());
            ultimaPagina = produtosFiltrado.getUltimaPagina();
        }

        ArrayList<Integer> produtosAdmListaIds = new ArrayList<>();
        produtosAdmLista.forEach(produto -> produtosAdmListaIds.add(produto.getId()));

        ArrayList<Integer> produtosPaginateIds = new ArrayList<>();
        produtosDoPaginate.forEach(produto -> produtosPaginateIds.add(produto.getId()));

        assertEquals(produtosAdmListaIds.toString(), produtosPaginateIds.toString());
    }

    @Test
    public void testaUpdateProduto() throws Exception {
        List<ProdutoDTO> produtos = _produtoService.listaProdutosAdm();
        ProdutoDTO ultimoProduto = produtos.get(produtos.size() - 1);
        ultimoProduto.setNome(ultimoProduto.getNome() + "x");

        _produtoService.atualizaProdutoPorId(ultimoProduto.getId(), ultimoProduto);

        assertEquals(ultimoProduto.toJson(), _produtoService.encontraProdutoPorId(ultimoProduto.getId()).toJson());
    }

    @Test
    public void testaIncrementoDaQuantidadeDeEstoque() throws Exception {
        Integer quantidadeIncremento = 2;

        ProdutoDTO primeiroProduto = _produtoService.listaProdutosAdm().get(0);
        _produtoService.insereQuantidadeEmEstoqueDoProdutoPorId(primeiroProduto.getId(), quantidadeIncremento);

        assertEquals(String.valueOf(primeiroProduto.getQuantidade() + quantidadeIncremento),
                String.valueOf(_produtoService.encontraProdutoPorId(primeiroProduto.getId()).getQuantidade()));
    }

    @Test
    public void testaToogleFavoritaProduto() throws Exception {
        Integer usuarioId = _usuarioService.listaUsuarios().get(0).getId();
        ProdutoDTO primeiroProduto = _produtoService.listaProdutosAdm().get(0);

        Boolean antes = _produtoService.produtoFavoritadoPeloUsuario(primeiroProduto.getId(), usuarioId);

        _produtoService.usuarioToogleFavoritaProdutoPorId(primeiroProduto.getId(), usuarioId);
        Boolean depois = _produtoService.produtoFavoritadoPeloUsuario(primeiroProduto.getId(), usuarioId);

        assertNotEquals(String.valueOf(antes), String.valueOf(depois));
    }

    @Test
    public void testaExclusaoProduto() throws Exception {
        List<ProdutoDTO> produtos = _produtoService.listaProdutosAdm();

        ProdutoDTO ultimoProduto = produtos.get(produtos.size() - 1);

        _produtoService.excluiProdutoPorId(ultimoProduto.getId());

        assertEquals(String.valueOf(produtos.size() - 1), String.valueOf(_produtoService.listaProdutosAdm().size()));
    }
}
