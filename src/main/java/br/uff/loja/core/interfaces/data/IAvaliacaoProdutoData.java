package br.uff.loja.core.interfaces.data;

import java.util.ArrayList;

import br.uff.loja.core.dtos.AvaliacaoProdutoInsertDTO;
import br.uff.loja.core.dtos.AvaliacaoProdutoListDTO;

public interface IAvaliacaoProdutoData {
    public Boolean jaFoiAvaliado(Integer usuarioId, Integer produtoId) throws Exception;
    public void insereAvaliacaoDoProduto(AvaliacaoProdutoInsertDTO avaliacaoProdutoInsertDTO) throws Exception;
    public ArrayList<AvaliacaoProdutoListDTO> recuperaAvaliacoesDeUmProduto(Integer produtoId) throws Exception;
}
