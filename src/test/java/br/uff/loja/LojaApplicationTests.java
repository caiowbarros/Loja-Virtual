package br.uff.loja;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import br.uff.loja.core.dtos.AvaliacaoProdutoInsertDTO;
import br.uff.loja.core.dtos.EnderecoDTO;
import br.uff.loja.core.interfaces.services.IAvaliacaoService;
import br.uff.loja.core.interfaces.services.IEnderecoService;
import br.uff.loja.core.services.AvaliacaoService;
import br.uff.loja.core.services.EnderecoService;

public class LojaApplicationTests {
    @Test
    public void testaAvaliacaoDuplicada() throws Exception {
        String exMessage = "";
        IAvaliacaoService avaliacaoService = new AvaliacaoService();
        Integer produtoId = 3;
        Integer usuarioId = 1;

        try {
            AvaliacaoProdutoInsertDTO avaliacaoProdutoInsertDTO = new AvaliacaoProdutoInsertDTO();
            avaliacaoProdutoInsertDTO.usuarioId = usuarioId;
            avaliacaoProdutoInsertDTO.descricao = "Testando descrição!";
            avaliacaoProdutoInsertDTO.produtoId = produtoId;
            avaliacaoProdutoInsertDTO.avaliacao = "4";
            avaliacaoProdutoInsertDTO.titulo = "Tô testando pois se gravar 2 vezes deu ruim!";
            
            avaliacaoService.avaliaProduto(avaliacaoProdutoInsertDTO);
            avaliacaoService.avaliaProduto(avaliacaoProdutoInsertDTO);
        } catch (Exception ex) {
            exMessage = ex.getMessage();
        }

        if(avaliacaoService.recuperaAvaliacoesDeUmProduto(produtoId).isEmpty()) {
            throw new Exception("A avaliação para o produto de id: " + produtoId + ", não foi realizada!");
        }

        assertEquals("O produto de id: 3 já foi avaliado!", exMessage);
    }

    @Test
    public void testaInclusaoEndereco() throws Exception {
        IEnderecoService enderecoService = new EnderecoService();

        EnderecoDTO endereco = new EnderecoDTO();
        endereco.nome = "Casa";
        endereco.logradouro = "Avenida Almirante Ary Parreiras, 6";
        endereco.cep = 24230322;
        endereco.cidade = "Niterói";
        endereco.estado = "RJ";
        endereco.pais = "Brasil";
        endereco.usuarioId = 1;

        Integer retorno = enderecoService.insereEndereco(endereco);

        assertEquals("1", String.valueOf(retorno));
    }

    @Test
    public void testaUpdateEndereco() throws Exception {
        IEnderecoService enderecoService = new EnderecoService();

        List<EnderecoDTO> enderecosDoUsuario = enderecoService.listaEnderecosPorUsuarioId(1);
        EnderecoDTO primeiroEndereco = enderecosDoUsuario.get(0);
        primeiroEndereco.nome += "x";

        enderecoService.atualizaEnderecoPorId(primeiroEndereco.id, primeiroEndereco);
        
        assertEquals(primeiroEndereco.toJson(), enderecoService.encontraEnderecoPorId(primeiroEndereco.id).toJson());
    }

    @Test
    public void testaExclusaoEndereco() throws Exception {
        IEnderecoService enderecoService = new EnderecoService();
        List<EnderecoDTO> enderecosDoUsuario = enderecoService.listaEnderecosPorUsuarioId(1);
        EnderecoDTO primeiroEndereco = enderecosDoUsuario.get(0);

        Integer retorno = enderecoService.excluiEnderecoPorId(primeiroEndereco.id);

        assertEquals("1", String.valueOf(retorno));
    }
}
