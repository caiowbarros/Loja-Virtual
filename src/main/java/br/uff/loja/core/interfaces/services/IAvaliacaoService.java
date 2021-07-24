package br.uff.loja.core.interfaces.services;

import br.uff.loja.core.dtos.AvaliacaoProdutoInsertDTO;

public interface IAvaliacaoService {
    public void avaliaProduto(AvaliacaoProdutoInsertDTO avaliacaoProdutoInsertDTO) throws Exception;
}
