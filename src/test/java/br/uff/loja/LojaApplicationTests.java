package br.uff.loja;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import br.uff.loja.core.dtos.AvaliacaoProdutoInsertDTO;
import br.uff.loja.core.dtos.CarrinhoDTO;
import br.uff.loja.core.dtos.CarrinhoProdutoDTO;
import br.uff.loja.core.dtos.EnderecoDTO;
import br.uff.loja.core.dtos.FiltraProdutoDTO;
import br.uff.loja.core.dtos.PaginateDTO;
import br.uff.loja.core.dtos.ProdutoDTO;
import br.uff.loja.core.dtos.ProdutoListaDTO;
import br.uff.loja.core.dtos.UsuarioDTO;
import br.uff.loja.core.dtos.VendaDTO;
import br.uff.loja.core.enums.EPermissaoUsuario;
import br.uff.loja.core.enums.EProdutoCategoria;
import br.uff.loja.core.interfaces.data.ICarrinhoData;
import br.uff.loja.core.interfaces.data.IProdutoData;
import br.uff.loja.core.interfaces.services.IAvaliacaoService;
import br.uff.loja.core.interfaces.services.ICarrinhoService;
import br.uff.loja.core.interfaces.services.IEnderecoService;
import br.uff.loja.core.interfaces.services.IProdutoService;
import br.uff.loja.core.interfaces.services.IUsuarioService;
import br.uff.loja.core.interfaces.services.IVendaService;
import br.uff.loja.core.services.AvaliacaoService;
import br.uff.loja.core.services.CarrinhoService;
import br.uff.loja.core.services.EnderecoService;
import br.uff.loja.core.services.ProdutoService;
import br.uff.loja.core.services.UsuarioService;
import br.uff.loja.infrastructure.data.CarrinhoData;
import br.uff.loja.infrastructure.data.ProdutoData;

public class LojaApplicationTests {
    @Test
    public void testaAvaliacaoDuplicada() throws Exception {
        String exMessage = "";
        IAvaliacaoService avaliacaoService = new AvaliacaoService();
        IProdutoService produtoService = new ProdutoService();
        IUsuarioService usuarioService = new UsuarioService();

        Integer produtoId = produtoService.listaProdutosAdm().get(0).getId();
        Integer usuarioId = usuarioService.listaUsuarios().get(0).getId();

        try {
            AvaliacaoProdutoInsertDTO avaliacaoProdutoInsertDTO = new AvaliacaoProdutoInsertDTO(usuarioId, produtoId, 4, "Testando descrição!", "Tô testando pois se gravar 2 vezes deu ruim!");
            
            avaliacaoService.avaliaProduto(avaliacaoProdutoInsertDTO);
            avaliacaoService.avaliaProduto(avaliacaoProdutoInsertDTO);
        } catch (Exception ex) {
            exMessage = ex.getMessage();
        }

        if(avaliacaoService.recuperaAvaliacoesDeUmProduto(produtoId).isEmpty()) {
            throw new Exception("A avaliação para o produto de id: " + produtoId + ", não foi realizada!");
        }

        assertEquals("O produto de id: " + produtoId + " já foi avaliado!", exMessage);
    }

    @Test
    public void testaInclusaoEndereco() throws Exception {
        IEnderecoService enderecoService = new EnderecoService();
        IUsuarioService usuarioService = new UsuarioService();

        Integer usuarioId = usuarioService.listaUsuarios().get(0).getId();

        Integer qtdEnderecosAntesInsercao = enderecoService.listaEnderecosPorUsuarioId(usuarioId).size();
        enderecoService.insereEndereco(new EnderecoDTO("Casa",usuarioId,24230322,"Avenida Almirante Ary Parreiras, 6","Niterói","RJ","Brasil"));

        assertEquals(String.valueOf(qtdEnderecosAntesInsercao + 1), String.valueOf(enderecoService.listaEnderecosPorUsuarioId(usuarioId).size()));
    }

    @Test
    public void testaUpdateEndereco() throws Exception {
        IEnderecoService enderecoService = new EnderecoService();

        Integer usuarioId = 1;

        List<EnderecoDTO> enderecosDoUsuario = enderecoService.listaEnderecosPorUsuarioId(usuarioId);
        
        if(enderecosDoUsuario.size() == 0) {
            this.testaInclusaoEndereco();
            enderecosDoUsuario = enderecoService.listaEnderecosPorUsuarioId(usuarioId);
        }
        
        EnderecoDTO primeiroEndereco = enderecosDoUsuario.get(0);
        primeiroEndereco.setNome(primeiroEndereco.getNome() + "x");

        enderecoService.atualizaEnderecoPorId(primeiroEndereco.getId(), primeiroEndereco);
        
        assertEquals(primeiroEndereco.toJson(), enderecoService.encontraEnderecoPorId(primeiroEndereco.getId()).toJson());
    }

    @Test
    public void testaExclusaoEndereco() throws Exception {
        IEnderecoService enderecoService = new EnderecoService();
        IUsuarioService usuarioService = new UsuarioService();

        Integer usuarioId = usuarioService.listaUsuarios().get(0).getId();
        List<EnderecoDTO> enderecosDoUsuario = enderecoService.listaEnderecosPorUsuarioId(usuarioId);
        
        if(enderecosDoUsuario.size() == 0) {
            this.testaInclusaoEndereco();
            enderecosDoUsuario = enderecoService.listaEnderecosPorUsuarioId(usuarioId);
        }

        EnderecoDTO primeiroEndereco = enderecosDoUsuario.get(0);

        enderecoService.excluiEnderecoPorId(primeiroEndereco.getId());

        assertEquals(String.valueOf(enderecosDoUsuario.size() - 1), String.valueOf(enderecoService.listaEnderecosPorUsuarioId(usuarioId).size()));
    }


    @Test
    public void testaInclusaoProduto() throws Exception {
        IProdutoService produtoService = new ProdutoService();

        Integer qtdProdutosAntesInsercao = produtoService.listaProdutosAdm().size();
        produtoService.insereProduto(new ProdutoDTO(
            null,
            "Playstation 6",
            2000000.55,
            "Super caro!",
            "url de uma imagem",
            EProdutoCategoria.PLAYSTATIONCONSOLES.getId(),
            1
        ));

        assertEquals(String.valueOf(qtdProdutosAntesInsercao + 1), String.valueOf(produtoService.listaProdutosAdm().size()));
    }

    @Test
    public void testaUpdateProduto() throws Exception {
        IProdutoService produtoService = new ProdutoService();

        List<ProdutoDTO> produtos = produtoService.listaProdutosAdm();
        ProdutoDTO ultimoProduto = produtos.get(produtos.size() - 1);
        ultimoProduto.setNome(ultimoProduto.getNome() + "x");

        produtoService.atualizaProdutoPorId(ultimoProduto.getId(), ultimoProduto);
        
        assertEquals(ultimoProduto.toJson(), produtoService.encontraProdutoPorId(ultimoProduto.getId()).toJson());
    }

    @Test
    public void testaIncrementoDaQuantidadeDeEstoque() throws Exception {
        IProdutoService produtoService = new ProdutoService();

        Integer quantidadeIncremento = 2;

        ProdutoDTO primeiroProduto = produtoService.listaProdutosAdm().get(0);
        produtoService.insereQuantidadeEmEstoqueDoProdutoPorId(primeiroProduto.getId(), quantidadeIncremento);

        assertEquals(String.valueOf(primeiroProduto.getQuantidade() + quantidadeIncremento), String.valueOf(produtoService.encontraProdutoPorId(primeiroProduto.getId()).getQuantidade()));
    }

    @Test
    public void testaToogleFavoritaProduto() throws Exception {
        IProdutoService produtoService = new ProdutoService();
        IProdutoData produtoData = new ProdutoData();

        IUsuarioService usuarioService = new UsuarioService();

        Integer usuarioId = usuarioService.listaUsuarios().get(0).getId();
        ProdutoDTO primeiroProduto = produtoService.listaProdutosAdm().get(0);

        Boolean antes = produtoData.produtoFavoritadoPeloUsuario(primeiroProduto.getId(), usuarioId);
        
        produtoService.usuarioToogleFavoritaProdutoPorId(primeiroProduto.getId(), usuarioId);
        Boolean depois = produtoData.produtoFavoritadoPeloUsuario(primeiroProduto.getId(), usuarioId);

        assertNotEquals(String.valueOf(antes), String.valueOf(depois));
    }

    @Test
    public void testaExclusaoProduto() throws Exception {
        IProdutoService produtoService = new ProdutoService();
        List<ProdutoDTO> produtos = produtoService.listaProdutosAdm();
        
        ProdutoDTO ultimoProduto = produtos.get(produtos.size() - 1);

        produtoService.excluiProdutoPorId(ultimoProduto.getId());

        assertEquals(String.valueOf(produtos.size() - 1), String.valueOf(produtoService.listaProdutosAdm().size()));
    }

    @Test
    public void testaLoginErro() throws Exception {
        IUsuarioService usuarioService = new UsuarioService();
        
        String msgErr = "";
        
        try {
            usuarioService.login("email@errado.com", "xxx");
        } catch (Exception e) {
            msgErr = e.getMessage();
        }

        assertEquals("Credenciais inválidas, por favor verifique os dados imputados e tente novamente!", msgErr);
    }

    @Test
    public void testaLoginSucesso() throws Exception {
        IUsuarioService usuarioService = new UsuarioService();

        UsuarioDTO usuario = usuarioService.listaUsuarios().get(0);

        UsuarioDTO usuarioLogin = usuarioService.login(usuario.getEmail(), usuario.getSenha());

        assertEquals(usuario.toJson(), usuarioLogin.toJson());
    }

    @Test
    public void testaUpdateUsuario() throws Exception {
        IUsuarioService usuarioService = new UsuarioService();

        List<UsuarioDTO> usuarios = usuarioService.listaUsuarios();
        UsuarioDTO primeiroUsuario = usuarios.get(0);
        primeiroUsuario.setNome(primeiroUsuario.getNome() + "x");

        UsuarioDTO usuarioAtualizado = usuarioService.gravaUsuario(primeiroUsuario);
        
        assertEquals(primeiroUsuario.toJson(), usuarioService.encontraUsuarioPorId(usuarioAtualizado.getId()).toJson());
    }

    @Test
    public void testaInsercaoUsuario() throws Exception {
        IUsuarioService usuarioService = new UsuarioService();

        UsuarioDTO novoUsuario = new UsuarioDTO(
            null,
            "Teste Insert User",
            "testando@example.com" + Math.random(),
            "123@SeiQueEhUmaSenhaRuim",
            EPermissaoUsuario.CLIENTE.getId()
        );

        UsuarioDTO usuarioGravado = usuarioService.gravaUsuario(novoUsuario);
        
        // setando id pra bater igualzinho apos insert
        novoUsuario.setId(usuarioGravado.getId());
        
        assertEquals(novoUsuario.toJson(), usuarioGravado.toJson());
    }

    @Test
    public void testaListaProdutosVitrine() throws Exception {
        IProdutoService produtoService = new ProdutoService();

        Integer paginaAtual = 0;
        Integer ultimaPagina = 1;

        Double precoMinimo = 20.1;
        Double precoMaximo = 28.8;

        List<ProdutoDTO> produtosAdmLista = produtoService.listaProdutosAdm();

        produtosAdmLista.removeIf(filter -> filter.getPreco() < precoMinimo || filter.getPreco() > precoMaximo);

        FiltraProdutoDTO filtro = new FiltraProdutoDTO();
        filtro.setItensPorPagina(5);
        filtro.setPrecoMinimo(precoMinimo);
        filtro.setPrecoMaximo(precoMaximo);

        List<ProdutoListaDTO> produtosDoPaginate = new ArrayList<>();

        while (ultimaPagina > paginaAtual) {
            paginaAtual += 1;
            filtro.setPaginaAtual(paginaAtual);

            PaginateDTO<List<ProdutoListaDTO>> produtosFiltrado = produtoService.listaProdutosVitrine(filtro);
            
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
    public void TestaInserirProdutoCarrinho() throws Exception {
        ICarrinhoService carrinhoService = new CarrinhoService();
        ICarrinhoData carrinhoData = new CarrinhoData();
        IUsuarioService usuarioService = new UsuarioService();
        IProdutoService produtoService = new ProdutoService();

        UsuarioDTO primeiroUsuario = usuarioService.listaUsuarios().get(0);
        ProdutoDTO primeiroProduto = produtoService.listaProdutosAdm().get(0);
        CarrinhoDTO carrinho = carrinhoService.recuperaCarrinhoAtivo(null, primeiroUsuario.getId(), "0.0.0.0");

        Integer qtdProdutoNoCarrinho = carrinhoData.quantidadeProdutoNoCarrinho(carrinho.getId(), primeiroProduto.getId());

        carrinhoService.insereProdutoCarrinho(carrinho.getId(), primeiroProduto.getId());
        
        assertEquals(String.valueOf(qtdProdutoNoCarrinho + 1), String.valueOf(carrinhoData.quantidadeProdutoNoCarrinho(carrinho.getId(), primeiroProduto.getId())));
    }
    
    @Test
    public void TestaRemoverProdutoCarrinho() throws Exception {
        ICarrinhoService carrinhoService = new CarrinhoService();
        IUsuarioService usuarioService = new UsuarioService();

        UsuarioDTO primeiroUsuario = usuarioService.listaUsuarios().get(0);
        CarrinhoDTO carrinho = carrinhoService.recuperaCarrinhoAtivo(null, primeiroUsuario.getId(), "0.0.0.0");

        List<CarrinhoProdutoDTO> produtos = carrinhoService.listaProdutosCarrinho(carrinho.getId());

        if (produtos.size() == 0) {
            this.TestaInserirProdutoCarrinho();
            produtos = carrinhoService.listaProdutosCarrinho(carrinho.getId());
        } 

        CarrinhoProdutoDTO primeiroProduto = produtos.get(0);
        carrinhoService.removeProdutoCarrinho(carrinho.getId(), primeiroProduto.getProdutoId());
        
        assertEquals(String.valueOf(produtos.size() - 1), String.valueOf(carrinhoService.listaProdutosCarrinho(carrinho.getId()).size()));
    }

    @Test
    public void TestaCompra() throws Exception {
        ICarrinhoService carrinhoService = new CarrinhoService();
        IUsuarioService usuarioService = new UsuarioService();
        IProdutoService produtoService = new ProdutoService();
        IVendaService vendaService = new VendaService();
        IEnderecoService enderecoService = new EnderecoService();

        UsuarioDTO primeiroUsuario = usuarioService.listaUsuarios().get(0);

        List<EnderecoDTO> enderecosDoUsuario = enderecoService.listaEnderecosPorUsuarioId(primeiroUsuario.getId());
        if(enderecosDoUsuario.size() == 0) {
            this.testaInclusaoEndereco();
            enderecosDoUsuario = enderecoService.listaEnderecosPorUsuarioId(primeiroUsuario.getId());
        }
        EnderecoDTO primeiroEndereco = enderecosDoUsuario.get(0);

        CarrinhoDTO carrinho = carrinhoService.recuperaCarrinhoAtivo(null, primeiroUsuario.getId(), "0.0.0.0");
        List<CarrinhoProdutoDTO> produtos = carrinhoService.listaProdutosCarrinho(carrinho.getId());
        if (produtos.size() == 0) {
            this.TestaInserirProdutoCarrinho();
            carrinhoService.insereProdutoCarrinho(carrinho.getId(), carrinhoService.listaProdutosCarrinho(carrinho.getId()).get(0).getId());
        }

        Integer qtdVendasDoUsuarioAntes = vendaService.listaVendasDoUsuario(primeiroUsuario.getId()).size();
        
        vendaService.gravaVenda(carrinho.getId(), primeiroEndereco.getId());

        assertEquals(String.valueOf(qtdVendasDoUsuarioAntes + 1), String.valueOf(vendaService.listaVendasDoUsuario(primeiroUsuario.getId()).size()));
    }

    @Test
    public void TestaCompraEnderecoOutroUsuario() throws Exception {
        ICarrinhoService carrinhoService = new CarrinhoService();
        IUsuarioService usuarioService = new UsuarioService();
        IProdutoService produtoService = new ProdutoService();
        IVendaService vendaService = new VendaService();
        IEnderecoService enderecoService = new EnderecoService();

        List<UsuarioDTO> listaUsuarios = usuarioService.listaUsuarios();

        UsuarioDTO primeiroUsuario = listaUsuarios.get(0);
        UsuarioDTO segundoUsuario = listaUsuarios.get(1);

        List<EnderecoDTO> enderecosDoUsuario = enderecoService.listaEnderecosPorUsuarioId(segundoUsuario.getId());
        if(enderecosDoUsuario.size() == 0) {
            enderecoService.insereEndereco(new EnderecoDTO("Casa",segundoUsuario.getId(),24241000,"Rua Doutor Mário Viana, 501","Niterói","RJ","Brasil"));
            enderecosDoUsuario = enderecoService.listaEnderecosPorUsuarioId(segundoUsuario.getId());
        }
        EnderecoDTO primeiroEndereco = enderecosDoUsuario.get(0);

        CarrinhoDTO carrinho = carrinhoService.recuperaCarrinhoAtivo(null, primeiroUsuario.getId(), "0.0.0.0");
        List<CarrinhoProdutoDTO> produtos = carrinhoService.listaProdutosCarrinho(carrinho.getId());
        if (produtos.size() == 0) {
            this.TestaInserirProdutoCarrinho();
            carrinhoService.insereProdutoCarrinho(carrinho.getId(), carrinhoService.listaProdutosCarrinho(carrinho.getId()).get(0).getId());
        }

        Integer qtdVendasDoUsuarioAntes = vendaService.listaVendasDoUsuario(primeiroUsuario.getId()).size();
        
        String msgErro = "";
        String erroEsperado = "O endereço escolhido de id:" + primeiroEndereco.getId() + " não pertence ao dono do carrinho (usuário de id: " + carrinho.getUsuarioId() + "), escolha outro endereço.";
        
        try {
            vendaService.gravaVenda(carrinho.getId(), primeiroEndereco.getId());
        } catch (Exception e) {
            msgErro = e.getMessage();
        }

        assertEquals(erroEsperado, msgErro);
    }
}
