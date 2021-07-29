package br.uff.loja.core.interfaces.services;

import java.util.List;

import br.uff.loja.core.dtos.AvaliacaoProdutoInsertDTO;
import br.uff.loja.core.dtos.AvaliacaoProdutoListDTO;

public interface IAvaliacaoService {
    public Integer avaliaProduto(AvaliacaoProdutoInsertDTO avaliacaoProdutoInsertDTO) throws Exception;
    public List<AvaliacaoProdutoListDTO> recuperaAvaliacoesDeUmProduto(Integer produtoId) throws Exception;
}
