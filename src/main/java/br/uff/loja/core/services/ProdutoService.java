package br.uff.loja.core.services;

import java.util.List;

import br.uff.loja.core.dtos.ProdutoDTO;
import br.uff.loja.core.dtos.ProdutoVitrineUsuarioDTO;
import br.uff.loja.core.exceptions.LojaException;
import br.uff.loja.core.interfaces.services.IProdutoService;

public class ProdutoService implements IProdutoService {

    @Override
    public ProdutoDTO encontraProdutoPorId(Integer id) throws LojaException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void excluiProdutoPorId(Integer id) throws LojaException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public ProdutoDTO atualizaProdutoPorId(Integer id, ProdutoDTO produto) throws LojaException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void insereQuantidadeEmEstoqueDoProdutoPorId(Integer id, Integer quantidade) throws LojaException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void insereProduto(ProdutoDTO produto) throws LojaException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void usuarioToogleFavoritaProdutoPorId(Integer produtoId, Integer usuarioId) throws LojaException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<ProdutoDTO> listaProdutosAdm() throws LojaException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ProdutoVitrineUsuarioDTO> listaProdutosVitrine() throws LojaException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ProdutoVitrineUsuarioDTO mostraProdutoVitrineParaUsuario(Integer id, Integer usuarioId)
            throws LojaException {
        // TODO Auto-generated method stub
        return null;
    }
}
