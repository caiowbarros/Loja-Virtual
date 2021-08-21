package br.uff.loja.core.interfaces.data;

import java.util.List;

import br.uff.loja.core.dtos.AvaliacaoProdutoInsertDTO;
import br.uff.loja.core.dtos.AvaliacaoProdutoListDTO;
import br.uff.loja.core.exceptions.LojaException;

public interface IAvaliacaoProdutoData {

    public Boolean jaFoiAvaliado(Integer usuarioId, Integer produtoId) throws LojaException;

    public void insereAvaliacaoDoProduto(AvaliacaoProdutoInsertDTO avaliacaoProdutoInsertDTO) throws LojaException;

    public List<AvaliacaoProdutoListDTO> recuperaAvaliacoesDeUmProduto(Integer produtoId) throws LojaException;
}
