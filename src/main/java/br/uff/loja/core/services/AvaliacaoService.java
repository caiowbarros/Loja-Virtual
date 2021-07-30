package br.uff.loja.core.services;

import java.util.List;

import br.uff.loja.core.dtos.AvaliacaoProdutoInsertDTO;
import br.uff.loja.core.dtos.AvaliacaoProdutoListDTO;
import br.uff.loja.core.interfaces.data.IAvaliacaoProdutoData;
import br.uff.loja.core.interfaces.services.IAvaliacaoService;
import br.uff.loja.infrastructure.data.AvaliacaoProdutoData;

public class AvaliacaoService implements IAvaliacaoService {
    private IAvaliacaoProdutoData avaliacaoProdutoData;

    public AvaliacaoService() {
        avaliacaoProdutoData = new AvaliacaoProdutoData();
    }

    @Override
    public Integer avaliaProduto(AvaliacaoProdutoInsertDTO avaliacaoProdutoInsertDTO) throws Exception {
        if(Boolean.FALSE.equals(avaliacaoProdutoData.jaFoiAvaliado(avaliacaoProdutoInsertDTO.getUsuarioId(), avaliacaoProdutoInsertDTO.getProdutoId()))) {
            return avaliacaoProdutoData.insereAvaliacaoDoProduto(avaliacaoProdutoInsertDTO);
        }
        throw new Exception("O produto de id: " + avaliacaoProdutoInsertDTO.getProdutoId() + " já foi avaliado!");
    }

    @Override
    public List<AvaliacaoProdutoListDTO> recuperaAvaliacoesDeUmProduto(Integer produtoId) throws Exception {
        return avaliacaoProdutoData.recuperaAvaliacoesDeUmProduto(produtoId);
    }
}
