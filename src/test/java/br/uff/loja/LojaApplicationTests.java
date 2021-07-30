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
            AvaliacaoProdutoInsertDTO avaliacaoProdutoInsertDTO = new AvaliacaoProdutoInsertDTO(usuarioId, produtoId, "4", "Testando descrição!", "Tô testando pois se gravar 2 vezes deu ruim!");
            
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

        Integer usuarioId = 1;

        Integer qtdEnderecosEsperadosDoUser = enderecoService.listaEnderecosPorUsuarioId(usuarioId).size();
        enderecoService.insereEndereco(new EnderecoDTO("Casa",usuarioId,24230322,"Avenida Almirante Ary Parreiras, 6","Niterói","RJ","Brasil"));

        assertEquals(String.valueOf(qtdEnderecosEsperadosDoUser + 1), String.valueOf(enderecoService.listaEnderecosPorUsuarioId(usuarioId).size()));
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
        List<EnderecoDTO> enderecosDoUsuario = enderecoService.listaEnderecosPorUsuarioId(1);
        EnderecoDTO primeiroEndereco = enderecosDoUsuario.get(0);

        Integer retorno = enderecoService.excluiEnderecoPorId(primeiroEndereco.getId());

        assertEquals("1", String.valueOf(retorno));
    }
}
