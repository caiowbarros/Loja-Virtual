package br.uff.loja.core.interfaces.data;


import java.util.List;

import br.uff.loja.core.dtos.AvaliacaoProdutoInsertDTO;
import br.uff.loja.core.dtos.AvaliacaoProdutoListDTO;

public interface IAvaliacaoProdutoData {
    public Boolean jaFoiAvaliado(Integer usuarioId, Integer produtoId) throws Exception;
    public Integer insereAvaliacaoDoProduto(AvaliacaoProdutoInsertDTO avaliacaoProdutoInsertDTO) throws Exception;
    public List<AvaliacaoProdutoListDTO> recuperaAvaliacoesDeUmProduto(Integer produtoId) throws Exception;
}
