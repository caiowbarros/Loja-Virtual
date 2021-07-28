package br.uff.loja;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import br.uff.loja.core.dtos.AvaliacaoProdutoInsertDTO;
import br.uff.loja.core.dtos.EnderecoDTO;
import br.uff.loja.core.interfaces.services.IAvaliacaoService;
import br.uff.loja.core.interfaces.services.IEnderecoService;
import br.uff.loja.core.services.AvaliacaoService;

public class LojaApplicationTests {
    @Test
    public void testaAvaliacaoDuplicada() throws Exception {
        String exMessage = "";
        try {
            IAvaliacaoService avaliacaoService = new AvaliacaoService();

            AvaliacaoProdutoInsertDTO avaliacaoProdutoInsertDTO = new AvaliacaoProdutoInsertDTO();
            avaliacaoProdutoInsertDTO.usuarioId = 1;
            avaliacaoProdutoInsertDTO.descricao = "Testando descrição!";
            avaliacaoProdutoInsertDTO.produtoId = 3;
            avaliacaoProdutoInsertDTO.avaliacao = "4";
            avaliacaoProdutoInsertDTO.titulo = "Tô testando pois se gravar 2 vezes deu merda!";
            
            avaliacaoService.avaliaProduto(avaliacaoProdutoInsertDTO);
            avaliacaoService.avaliaProduto(avaliacaoProdutoInsertDTO);
        } catch (Exception ex) {
            exMessage = ex.getMessage();
        }
        assertEquals("O produto de id: 3 já foi avaliado!", exMessage);
    }

    public void testaInclusaoEndereco() {
        IEnderecoService enderecoService = new EnderecoService();

        EnderecoDTO endereco = new EnderecoDTO();
        endereco.nome = "Casa";
        endereco.logradouro = "Avenida Almirante Ary Parreiras, 6";
        endereco.cep = "24230-322";
        endereco.cidade = "Niterói";
        endereco.estado = "RJ";
        endereco.pais = "Brasil";
        endereco.usuarioId = 1;

        enderecoService.insereEndereco(endereco);
    }

    public void testaUpdateEndereco() {
        IEnderecoService enderecoService = new EnderecoService();

        List<EnderecoDTO> enderecosDoUsuario = enderecoService.listaEnderecosPorUsuarioId(1);
        EnderecoDTO primeiroEndereco = enderecosDoUsuario.get(0);
        primeiroEndereco.nome += "x";

        enderecoService.atualizaEnderecoPorId(primeiroEndereco.id, primeiroEndereco);
    }

    public void testaExclusaoEndereco() {
        IEnderecoService enderecoService = new EnderecoService();
        List<EnderecoDTO> enderecosDoUsuario = enderecoService.listaEnderecosPorUsuarioId(1);
        EnderecoDTO primeiroEndereco = enderecosDoUsuario.get(0);

        enderecoService.excluiEnderecoPorId(primeiroEndereco.id);

        enderecoService.encontraEnderecoPorId(primeiroEndereco.id);
    }
}
