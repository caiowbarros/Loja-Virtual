package br.uff.loja.core.services;

import br.uff.loja.core.dtos.AvaliacaoProdutoInsertDTO;
import br.uff.loja.core.interfaces.data.IAvaliacaoProdutoData;
import br.uff.loja.core.interfaces.services.IAvaliacaoService;

public class AvaliacaoService implements IAvaliacaoService {
    private IAvaliacaoProdutoData avaliacaoProdutoData;

    @Override
    public void avaliaProduto(AvaliacaoProdutoInsertDTO avaliacaoProdutoInsertDTO) throws Exception {
        if(!avaliacaoProdutoData.jaFoiAvaliado(avaliacaoProdutoInsertDTO.usuarioId, avaliacaoProdutoInsertDTO.produtoId)) {
            avaliacaoProdutoData.insereAvaliacaoDoProduto(avaliacaoProdutoInsertDTO);
        }
    }
}
