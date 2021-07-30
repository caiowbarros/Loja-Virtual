package br.uff.loja.core.interfaces.services;

import java.util.List;

import br.uff.loja.core.dtos.ProdutoDTO;
import br.uff.loja.core.dtos.ProdutoVitrineUsuarioDTO;
import br.uff.loja.core.exceptions.LojaException;

public interface IProdutoService {
    public ProdutoDTO encontraProdutoPorId(Integer id) throws LojaException;
    public Integer excluiProdutoPorId(Integer id) throws LojaException;
    public Integer atualizaProdutoPorId(Integer id, ProdutoDTO produto) throws LojaException;
    public Integer insereQuantidadeEmEstoqueDoProdutoPorId(Integer id, Integer quantidade) throws LojaException;
    public Integer insereProduto(ProdutoDTO produto) throws LojaException;
    public Integer usuarioToogleFavoritaProdutoPorId(Integer produtoId, Integer usuarioId) throws LojaException;
    public List<ProdutoDTO> listaProdutosAdm() throws LojaException;
    public List<ProdutoDTO> listaProdutosVitrine() throws LojaException;
    public ProdutoVitrineUsuarioDTO mostraProdutoVitrineParaUsuario(Integer id, Integer usuarioId) throws LojaException;
}
