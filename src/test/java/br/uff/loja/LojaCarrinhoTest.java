package br.uff.loja;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.uff.loja.core.dtos.CarrinhoDTO;
import br.uff.loja.core.dtos.CarrinhoProdutoDTO;
import br.uff.loja.core.dtos.EnderecoDTO;
import br.uff.loja.core.dtos.ProdutoDTO;
import br.uff.loja.core.dtos.UsuarioDTO;
import br.uff.loja.core.interfaces.services.ICarrinhoService;
import br.uff.loja.core.interfaces.services.IEnderecoService;
import br.uff.loja.core.interfaces.services.IProdutoService;
import br.uff.loja.core.interfaces.services.IUsuarioService;
import br.uff.loja.core.interfaces.services.IVendaService;
import br.uff.loja.core.services.CarrinhoService;
import br.uff.loja.core.services.EnderecoService;
import br.uff.loja.core.services.ProdutoService;
import br.uff.loja.core.services.UsuarioService;
import br.uff.loja.core.services.VendaService;

public class LojaCarrinhoTest {
    private ICarrinhoService _carrinhoService;
    private IUsuarioService _usuarioService;
    private IProdutoService _produtoService;
    private IVendaService _vendaService;
    private IEnderecoService _enderecoService;

    @Before
    public void setUp() throws Exception {
        _carrinhoService = new CarrinhoService();
        _usuarioService = new UsuarioService();
        _produtoService = new ProdutoService();
        _vendaService = new VendaService();
        _enderecoService = new EnderecoService();
    }

    @Test
    public void TestaInserirProdutoCarrinho() throws Exception {
        UsuarioDTO primeiroUsuario = _usuarioService.listaUsuarios().get(0);
        ProdutoDTO primeiroProduto = _produtoService.listaProdutosAdm().get(0);
        CarrinhoDTO carrinho = _carrinhoService.recuperaCarrinhoAtivo(null, primeiroUsuario.getId(), "0.0.0.0");

        Integer qtdProdutoNoCarrinho = _carrinhoService.quantidadeProdutoNoCarrinho(carrinho.getId(),
                primeiroProduto.getId());

        _carrinhoService.insereProdutoCarrinho(carrinho.getId(), primeiroProduto.getId());

        assertEquals(String.valueOf(qtdProdutoNoCarrinho + 1), String
                .valueOf(_carrinhoService.quantidadeProdutoNoCarrinho(carrinho.getId(), primeiroProduto.getId())));
    }

    @Test
    public void TestaRemoverProdutoCarrinho() throws Exception {
        UsuarioDTO primeiroUsuario = _usuarioService.listaUsuarios().get(0);
        CarrinhoDTO carrinho = _carrinhoService.recuperaCarrinhoAtivo(null, primeiroUsuario.getId(), "0.0.0.0");

        List<CarrinhoProdutoDTO> produtos = _carrinhoService.listaProdutosCarrinho(carrinho.getId());

        if (produtos.size() == 0) {
            this.TestaInserirProdutoCarrinho();
            produtos = _carrinhoService.listaProdutosCarrinho(carrinho.getId());
        }

        CarrinhoProdutoDTO primeiroProduto = produtos.get(0);
        _carrinhoService.removeProdutoCarrinho(carrinho.getId(), primeiroProduto.getProdutoId());

        assertEquals(String.valueOf(produtos.size() - 1),
                String.valueOf(_carrinhoService.listaProdutosCarrinho(carrinho.getId()).size()));
    }

    @Test
    public void TestaCompra() throws Exception {
        UsuarioDTO primeiroUsuario = _usuarioService.listaUsuarios().get(0);

        List<EnderecoDTO> enderecosDoUsuario = _enderecoService.listaEnderecosPorUsuarioId(primeiroUsuario.getId());
        if (enderecosDoUsuario.size() == 0) {
            _enderecoService.insereEndereco(new EnderecoDTO("Casa", primeiroUsuario.getId(), 24230322,
                    "Avenida Almirante Ary Parreiras, 6", "Niterói", "RJ", "Brasil"));
            enderecosDoUsuario = _enderecoService.listaEnderecosPorUsuarioId(primeiroUsuario.getId());
        }
        EnderecoDTO primeiroEndereco = enderecosDoUsuario.get(0);

        CarrinhoDTO carrinho = _carrinhoService.recuperaCarrinhoAtivo(null, primeiroUsuario.getId(), "0.0.0.0");
        List<CarrinhoProdutoDTO> produtos = _carrinhoService.listaProdutosCarrinho(carrinho.getId());
        if (produtos.size() == 0) {
            this.TestaInserirProdutoCarrinho();
        }

        Integer qtdVendasDoUsuarioAntes = _vendaService.listaVendasDoUsuario(primeiroUsuario.getId()).size();

        _vendaService.gravaVenda(primeiroUsuario.getId(), carrinho.getId(), primeiroEndereco.getId());

        assertEquals(String.valueOf(qtdVendasDoUsuarioAntes + 1),
                String.valueOf(_vendaService.listaVendasDoUsuario(primeiroUsuario.getId()).size()));
    }

    @Test
    public void TestaCompraEnderecoOutroUsuario() throws Exception {
        List<UsuarioDTO> listaUsuarios = _usuarioService.listaUsuarios();

        UsuarioDTO primeiroUsuario = listaUsuarios.get(0);
        UsuarioDTO segundoUsuario = listaUsuarios.get(1);

        List<EnderecoDTO> enderecosDoUsuario = _enderecoService.listaEnderecosPorUsuarioId(segundoUsuario.getId());
        if (enderecosDoUsuario.size() == 0) {
            _enderecoService.insereEndereco(new EnderecoDTO("Casa", segundoUsuario.getId(), 24241000,
                    "Rua Doutor Mário Viana, 501", "Niterói", "RJ", "Brasil"));
            enderecosDoUsuario = _enderecoService.listaEnderecosPorUsuarioId(segundoUsuario.getId());
        }
        EnderecoDTO primeiroEndereco = enderecosDoUsuario.get(0);

        CarrinhoDTO carrinho = _carrinhoService.recuperaCarrinhoAtivo(null, primeiroUsuario.getId(), "0.0.0.0");
        List<CarrinhoProdutoDTO> produtos = _carrinhoService.listaProdutosCarrinho(carrinho.getId());
        if (produtos.size() == 0) {
            _carrinhoService.insereProdutoCarrinho(carrinho.getId(), _produtoService.listaProdutosAdm().get(0).getId());
        }

        String msgErro = "";
        String erroEsperado = "O endereço escolhido de id:" + primeiroEndereco.getId()
                + " não pertence ao dono do carrinho (usuário de id: " + carrinho.getUsuarioId()
                + "), escolha outro endereço.";

        try {
            _vendaService.gravaVenda(primeiroUsuario.getId(), carrinho.getId(), primeiroEndereco.getId());
        } catch (Exception e) {
            msgErro = e.getMessage();
        }

        assertEquals(erroEsperado, msgErro);
    }
}
