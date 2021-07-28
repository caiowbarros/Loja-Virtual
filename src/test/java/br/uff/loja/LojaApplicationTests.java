package br.uff.loja;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.uff.loja.core.dtos.AvaliacaoProdutoInsertDTO;
import br.uff.loja.core.interfaces.services.IAvaliacaoService;
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
}
