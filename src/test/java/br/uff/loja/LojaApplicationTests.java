package br.uff.loja;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.List;

import org.junit.Test;

import br.uff.loja.core.dtos.AvaliacaoProdutoInsertDTO;
import br.uff.loja.core.dtos.EnderecoDTO;
import br.uff.loja.core.dtos.ProdutoDTO;
import br.uff.loja.core.interfaces.data.IProdutoData;
import br.uff.loja.core.interfaces.services.IAvaliacaoService;
import br.uff.loja.core.interfaces.services.IEnderecoService;
import br.uff.loja.core.interfaces.services.IProdutoService;
import br.uff.loja.core.services.AvaliacaoService;
import br.uff.loja.core.services.EnderecoService;
import br.uff.loja.core.services.ProdutoService;
import br.uff.loja.infrastructure.data.ProdutoData;

public class LojaApplicationTests {
    @Test
    public void testaAvaliacaoDuplicada() throws Exception {
        String exMessage = "";
        IAvaliacaoService avaliacaoService = new AvaliacaoService();
        IProdutoService produtoService = new ProdutoService();

        Integer produtoId = produtoService.listaProdutosAdm().get(0).getId();
        Integer usuarioId = 1;

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

        Integer usuarioId = 1;

        Integer qtdEnderecosAntesInsercao = enderecoService.listaEnderecosPorUsuarioId(usuarioId).size();
        enderecoService.insereEndereco(new EnderecoDTO("Casa",usuarioId,24230322,"Avenida Almirante Ary Parreiras, 6","Niterói","RJ","Brasil"));

        assertEquals(String.valueOf(qtdEnderecosAntesInsercao + 1), String.valueOf(enderecoService.listaEnderecosPorUsuarioId(usuarioId).size()));
    }

    @Test
    public void testaUpdateEndereco() throws Exception {
        IEnderecoService enderecoService = new EnderecoService();

        List<EnderecoDTO> enderecosDoUsuario = enderecoService.listaEnderecosPorUsuarioId(1);
        EnderecoDTO primeiroEndereco = enderecosDoUsuario.get(0);
        primeiroEndereco.setNome(primeiroEndereco.getNome() + "x");

        enderecoService.atualizaEnderecoPorId(primeiroEndereco.getId(), primeiroEndereco);
        
        assertEquals(primeiroEndereco.toJson(), enderecoService.encontraEnderecoPorId(primeiroEndereco.getId()).toJson());
    }

    @Test
    public void testaExclusaoEndereco() throws Exception {
        IEnderecoService enderecoService = new EnderecoService();
        Integer usuarioId = 1;
        List<EnderecoDTO> enderecosDoUsuario = enderecoService.listaEnderecosPorUsuarioId(usuarioId);
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
            5,
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

        Integer usuarioId = 1;
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
}
