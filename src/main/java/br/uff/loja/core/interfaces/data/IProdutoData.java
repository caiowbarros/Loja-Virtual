package br.uff.loja.core.interfaces.data;

import java.util.List;

import br.uff.loja.core.dtos.FiltraProdutoDTO;
import br.uff.loja.core.dtos.PaginateDTO;
import br.uff.loja.core.dtos.ProdutoDTO;
import br.uff.loja.core.dtos.ProdutoHomeDTO;
import br.uff.loja.core.dtos.ProdutoListaDTO;
import br.uff.loja.core.dtos.ProdutoVitrineUsuarioDTO;
import br.uff.loja.core.exceptions.LojaException;

public interface IProdutoData {

    public ProdutoDTO encontraProdutoPorId(Integer id) throws LojaException;

    public void excluiProdutoPorId(Integer id) throws LojaException;

    public void atualizaProdutoPorId(Integer id, ProdutoDTO produto) throws LojaException;

    public void insereQuantidadeEmEstoqueDoProdutoPorId(Integer id, Integer quantidade) throws LojaException;

    public void insereProduto(ProdutoDTO produto) throws LojaException;

    public List<ProdutoDTO> listaProdutosAdm() throws LojaException;

    public List<ProdutoHomeDTO> listaProdutosBanner() throws LojaException;

    public PaginateDTO<List<ProdutoListaDTO>> listaProdutosVitrine(FiltraProdutoDTO filtro) throws LojaException;

    public ProdutoVitrineUsuarioDTO mostraProdutoVitrineParaUsuario(Integer id, Integer usuarioId) throws LojaException;

    public Boolean produtoFavoritadoPeloUsuario(Integer produtoId, Integer usuarioId) throws LojaException;

    public void removeFavoritacaoProdutoPeloUsuario(Integer produtoId, Integer usuarioId) throws LojaException;

    public void adicionaFavoritacaoProdutoPeloUsuario(Integer produtoId, Integer usuarioId) throws LojaException;
}
