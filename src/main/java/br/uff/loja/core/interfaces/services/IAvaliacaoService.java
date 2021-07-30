package br.uff.loja.core.interfaces.services;

import java.util.List;

import br.uff.loja.core.dtos.AvaliacaoProdutoInsertDTO;
import br.uff.loja.core.dtos.AvaliacaoProdutoListDTO;
import br.uff.loja.core.exceptions.LojaException;

public interface IAvaliacaoService {
    public void avaliaProduto(AvaliacaoProdutoInsertDTO avaliacaoProdutoInsertDTO) throws LojaException;
    public List<AvaliacaoProdutoListDTO> recuperaAvaliacoesDeUmProduto(Integer produtoId) throws LojaException;
}
