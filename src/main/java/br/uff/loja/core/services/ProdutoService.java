package br.uff.loja.core.services;

import java.util.List;

import br.uff.loja.core.dtos.FiltraProdutoDTO;
import br.uff.loja.core.dtos.PaginateDTO;
import br.uff.loja.core.dtos.ProdutoDTO;
import br.uff.loja.core.dtos.ProdutoHomeDTO;
import br.uff.loja.core.dtos.ProdutoListaDTO;
import br.uff.loja.core.dtos.ProdutoVitrineUsuarioDTO;
import br.uff.loja.core.exceptions.LojaException;
import br.uff.loja.core.interfaces.data.IProdutoData;
import br.uff.loja.core.interfaces.services.IProdutoService;
import br.uff.loja.infrastructure.data.ProdutoData;

public class ProdutoService implements IProdutoService {
    private IProdutoData produtoData;

    public ProdutoService() {
        produtoData = new ProdutoData();
    }

    @Override
    public ProdutoDTO encontraProdutoPorId(Integer id) throws LojaException {
        return produtoData.encontraProdutoPorId(id);
    }

    @Override
    public void excluiProdutoPorId(Integer id) throws LojaException {
        produtoData.excluiProdutoPorId(id);        
    }

    @Override
    public ProdutoDTO atualizaProdutoPorId(Integer id, ProdutoDTO produto) throws LojaException {
        produtoData.atualizaProdutoPorId(id, produto);
        return produtoData.encontraProdutoPorId(id);
    }

    @Override
    public void insereQuantidadeEmEstoqueDoProdutoPorId(Integer id, Integer quantidade) throws LojaException {
        produtoData.insereQuantidadeEmEstoqueDoProdutoPorId(id, quantidade);
    }

    @Override
    public void insereProduto(ProdutoDTO produto) throws LojaException {
        produtoData.insereProduto(produto);
    }

    @Override
    public void usuarioToogleFavoritaProdutoPorId(Integer produtoId, Integer usuarioId) throws LojaException {
        if(Boolean.TRUE.equals(produtoData.produtoFavoritadoPeloUsuario(produtoId, usuarioId))) {
            produtoData.removeFavoritacaoProdutoPeloUsuario(produtoId, usuarioId);
        } else {
            produtoData.adicionaFavoritacaoProdutoPeloUsuario(produtoId, usuarioId);
        }
    }

    @Override
    public List<ProdutoDTO> listaProdutosAdm() throws LojaException {
        return produtoData.listaProdutosAdm();
    }

    @Override
    public PaginateDTO<List<ProdutoListaDTO>> listaProdutosVitrine(FiltraProdutoDTO filtro) throws LojaException {
        return produtoData.listaProdutosVitrine(filtro);
    }

    @Override
    public ProdutoVitrineUsuarioDTO mostraProdutoVitrineParaUsuario(Integer id, Integer usuarioId) throws LojaException {
        return produtoData.mostraProdutoVitrineParaUsuario(id, usuarioId);
    }

    @Override
    public List<ProdutoHomeDTO> listaProdutosBanner() throws LojaException {
        return produtoData.listaProdutosBanner();
    }
}
